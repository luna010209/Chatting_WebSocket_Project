package com.example.contact.prompt;

public final class PromptBuilder {
    private PromptBuilder(){}

    public static String buildPrompt(String history, String newMessage) {
        return GeneralPrompt.TEMPLATE
                .replace("{latest_messages}", history)
                .replace("{new_message}", newMessage);
    }

}
