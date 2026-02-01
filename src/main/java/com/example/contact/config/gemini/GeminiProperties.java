package com.example.contact.config.gemini;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gemini")
public record GeminiProperties(
        String apiKey,
        Models models
) {
    public record Models(String image, String text) {}
}
