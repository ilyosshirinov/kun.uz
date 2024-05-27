package com.example.entity;

import com.example.enums.SmsStatus;
import com.example.enums.SmsType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sms_history")
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "message")
    private String message;

    @Column(name = "code")
    private String code;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SmsStatus status = SmsStatus.NEW;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SmsType type = SmsType.STANDARD;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
