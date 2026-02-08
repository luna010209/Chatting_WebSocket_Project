package com.example.contact.contact.socket;

import com.example.contact.contact.dto.PromptRequest;
import com.example.contact.contact.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class AiSocket {
    private final AiService aiService;

    @MessageMapping("/ai-chat")
    @SendTo("/topic/ai-chat")
    public String aiResponse(PromptRequest request){
        return aiService.chatWithAI(request);
    }

}
