package com.example.contact.aiGenerate;

import com.example.contact.config.gemini.GeminiProperties;
import com.example.contact.exceptionHandler.CustomException;
import com.google.genai.Client;
import com.google.genai.ResponseStream;
import com.google.genai.types.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class GeminiImage {

    private final Client client;
    private final String model;

    public GeminiImage(Client client, GeminiProperties props){
        this.client = client;
        this.model = props.models().image();
    }

    public byte[] basicImage(String prompt){
        List<Content> contents = List.of(
                Content.builder()
                        .role("user")
                        .parts(List.of(Part.fromText(prompt)))
                        .build()
        );

        GenerateContentConfig config = GenerateContentConfig.builder()
                .responseModalities(List.of("IMAGE", "TEXT"))
                .build();

        ResponseStream<GenerateContentResponse> stream =
                client.models.generateContentStream(model, contents, config);

        try{
            for (GenerateContentResponse res:stream){
                if (res.candidates().isEmpty()) continue;

                var contentOpt = res.candidates().get().get(0).content();
                if (contentOpt.isEmpty()) continue;

                for (Part part : contentOpt.get().parts().orElse(List.of())){
                    if (part.inlineData().isPresent()){
                        Blob blob = part.inlineData().get();
                        byte[] imageBytes = blob.data().orElseThrow(
                                () -> new CustomException(HttpStatus.BAD_REQUEST, "No image exist!")
                        );

                        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

                        ByteArrayOutputStream jpgOut = new ByteArrayOutputStream();
                        ImageIO.write(bufferedImage, "jpg", jpgOut);

                        byte[] jpgBytes = jpgOut.toByteArray();
                        return jpgBytes;
                    }
                }
            }
        } catch (IOException e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } finally {
            stream.close();
        }

        throw new CustomException(HttpStatus.BAD_REQUEST, "No image was generated.");
    }
}
