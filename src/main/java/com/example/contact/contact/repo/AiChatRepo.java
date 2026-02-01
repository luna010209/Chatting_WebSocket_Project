package com.example.contact.contact.repo;

import com.example.contact.contact.entity.AIChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface AiChatRepo extends JpaRepository<AIChat, Long> {
    @Query("SELECT ai FROM AIChat ai ORDER BY ai.createdAt DESC")
    List<AIChat> findRecentChats(Pageable pageable);

    List<AIChat> findTop200ByOrderByCreatedAtDesc();
}
