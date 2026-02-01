package com.example.contact.config.gemini;

import com.example.contact.exceptionHandler.CustomException;
import com.google.genai.Client;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
@EnableConfigurationProperties(GeminiProperties.class)
public class GeminiConfig {

    private final GeminiProperties props;

    public GeminiConfig(GeminiProperties props){
        this.props = props;

        if (props.apiKey() == null || props.apiKey().isBlank()){
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "GEMINI_APIKEY is missing");
        }
    }

    @Bean
    public Client geminiClient(){
        return Client.builder()
                .apiKey(props.apiKey())
                .build();
    }
}
