package com.example.contact.contact.entity;

import com.example.contact.authentication.user.entity.UserInfo;
import com.example.contact.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contact extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserInfo sender;
    @ManyToOne
    private UserInfo receiver;
    private String content;
    private boolean confirm;
    private boolean edit;

    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status {
        Connecting, OnCall, Hangup, Reject, Miss, Message
    }
}
