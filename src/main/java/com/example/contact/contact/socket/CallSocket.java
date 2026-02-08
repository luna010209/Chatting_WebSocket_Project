package com.example.contact.contact.socket;

import com.example.contact.contact.dto.ContactDto;
import com.example.contact.contact.dto.ContactRequestDto;
import com.example.contact.contact.service.CallService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class   CallSocket {
    private final CallService callService;

    @MessageMapping("/signaling")
    @SendTo("/topic/signaling")
    public ContactDto makeCall(ContactRequestDto dto){
        return callService.makeCall(dto);
    }

    @MessageMapping("/on-call")
    @SendTo("/topic/on-call")
    public ContactDto onCall(ContactRequestDto dto){
        return callService.onCall(dto);
    }

    @MessageMapping("/hangup")
    @SendTo("/topic/hangup")
    public ContactDto hangup(ContactRequestDto dto){
        return callService.hangup(dto);
    }

    @MessageMapping("/reject")
    @SendTo("/topic/reject")
    public ContactDto reject(ContactRequestDto dto){
        return callService.reject(dto);
    }

    @MessageMapping("/miss-call")
    @SendTo("/topic/miss-call")
    public ContactDto missCall(ContactRequestDto dto){
        return callService.missCall(dto);
    }

    @MessageMapping("/offer")
    @SendTo("/topic/offer")
    public String createOffer(@Payload String offer){
        return offer;
    }

    @MessageMapping("/answer")
    @SendTo("/topic/answer")
    public String createAnswer(@Payload String answer){
        return answer;
    }

    @MessageMapping("/candidate")
    @SendTo("/topic/candidate")
    public String candidate(@Payload String candidate){
        return candidate;
    }
}
