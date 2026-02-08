package com.example.contact.contact.service;

import com.example.contact.aiGenerate.GeminiText;
import com.example.contact.authentication.component.UserComponent;
import com.example.contact.authentication.user.entity.UserInfo;
import com.example.contact.contact.dto.AiChatResponse;
import com.example.contact.contact.dto.PromptRequest;
import com.example.contact.contact.entity.AIChat;
import com.example.contact.contact.repo.AiChatRepo;
import com.example.contact.prompt.PromptBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AiService {
    private final AiChatRepo aiChatRepo;
    private final UserComponent userComponent;
    private final GeminiText geminiText;

    public String chatWithAI(PromptRequest request){
        UserInfo currentUser = userComponent.userLogin();

        AIChat userChat = AIChat.builder()
                .user(currentUser)
                .aiResponse(false)
                .content(request.request())
                .createdAt(LocalDateTime.now())
                .build();

        aiChatRepo.save(userChat);

        StringBuilder sb = new StringBuilder();

        List<AIChat> chats = aiChatRepo.findTop200ByOrderByCreatedAtDesc();

        for (AIChat chat:chats){
            String speaker = chat.isAiResponse() ? "AI" : "User";

            sb.append(speaker)
                    .append(": ")
                    .append(chat.getContent())
                    .append("\n");
        }

        String prompt = PromptBuilder.buildPrompt(sb.toString(), request.request());

        String rawAns = geminiText.basicText(prompt);

        AIChat aiResponse = AIChat.builder()
                .user(currentUser)
                .aiResponse(true)
                .content(rawAns)
                .createdAt(LocalDateTime.now())
                .build();

        aiChatRepo.save(aiResponse);

        return rawAns;
    }

    public List<AiChatResponse> historyChat(){
        UserInfo currentUser = userComponent.userLogin();
        return aiChatRepo.findByUser(currentUser)
                .stream().map(AiChatResponse::fromEntity).toList();
    }
}
