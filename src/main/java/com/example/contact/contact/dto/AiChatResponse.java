package com.example.contact.contact.dto;

import com.example.contact.contact.entity.AIChat;

public record AiChatResponse(
        String content,
        boolean aiResponse
) {
    public static AiChatResponse fromEntity (AIChat aiChat){
        return new AiChatResponse(
                aiChat.getContent(),
                aiChat.isAiResponse()
        );
    }
}
