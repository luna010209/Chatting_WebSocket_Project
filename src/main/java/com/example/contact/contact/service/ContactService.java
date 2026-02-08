package com.example.contact.contact.service;

import com.example.contact.authentication.component.UserComponent;
import com.example.contact.authentication.user.entity.UserInfo;
import com.example.contact.contact.dto.AiChatResponse;
import com.example.contact.contact.dto.ContactDto;
import com.example.contact.contact.dto.ContactRequestDto;
import com.example.contact.contact.entity.AIChat;
import com.example.contact.contact.entity.Contact;
import com.example.contact.contact.repo.ContactRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepo contactRepo;
    private final UserComponent userComponent;

    public List<ContactDto> chatBox(Long receiverId){
        UserInfo sender = userComponent.userLogin();
        UserInfo receiver = userComponent.userById(receiverId);
        List<ContactDto> list = new ArrayList<>();
        for (Contact contact: contactRepo.chatBox(sender, receiver)){
            list.add(ContactDto.dto(contact));
        }
        return list;
    }
}
