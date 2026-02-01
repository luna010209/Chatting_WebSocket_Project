package com.example.contact.contact.entity;

import com.example.contact.authentication.user.entity.UserInfo;
import jakarta.persistence.*;
import lombok.*;

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

    private String content;

    @ManyToOne
    private UserInfo user;

    private boolean aiResponse;
    private boolean edit;
}
