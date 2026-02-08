package com.example.contact.contact.http;

import com.example.contact.contact.dto.AiChatResponse;
import com.example.contact.contact.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai-chat")
public class AiHttp {
    private final AiService aiService;

    @GetMapping
    public List<AiChatResponse> historyChat(){
        return aiService.historyChat();
    }
}
