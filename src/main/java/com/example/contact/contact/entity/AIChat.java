package com.example.contact.contact.entity;

import com.example.contact.authentication.user.entity.UserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AIChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @ManyToOne
    private UserInfo user;

    private boolean aiResponse;
    private boolean edit;
    private LocalDateTime createdAt;
}
