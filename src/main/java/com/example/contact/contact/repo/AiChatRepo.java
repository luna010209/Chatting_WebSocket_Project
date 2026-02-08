package com.example.contact.contact.repo;

import com.example.contact.authentication.user.entity.UserInfo;
import com.example.contact.contact.entity.AIChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiChatRepo extends JpaRepository<AIChat, Long> {

    List<AIChat> findTop200ByOrderByCreatedAtDesc();

    List<AIChat> findByUser(UserInfo user);
}
