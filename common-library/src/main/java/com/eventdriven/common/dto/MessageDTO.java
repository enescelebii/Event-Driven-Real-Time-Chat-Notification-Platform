package com.eventdriven.common.dto;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class MessageDTO {
    private Long id;
    private Long senderId;
    private Long receiverId; // private chat için
    private Long roomId;     // grup chat için
    private String content;
    private Instant timestamp;
}