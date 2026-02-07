package com.example.contact.aiGenerate;

import com.example.contact.config.gemini.GeminiProperties;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Component;

@Component
public class GeminiText {
    private final Client client;
    private final String model;

    public GeminiText(Client client, GeminiProperties props){
        this.client = client;
        this.model = props.models().text();
    }

    public String basicText(String prompt){
        GenerateContentResponse response =
            client.models.generateContent(
                model,
                prompt,
                null
            );
        return response.text();
    }
}
