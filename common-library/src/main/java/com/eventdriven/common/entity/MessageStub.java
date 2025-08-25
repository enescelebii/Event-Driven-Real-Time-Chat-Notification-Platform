package com.eventdriven.common.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class MessageStub {
    private Long id;
    private Long senderId;
    private Long receiverId; // private chat için
    private Long roomId;     // grup chat için
    private String content;
    private Instant timestamp;
}