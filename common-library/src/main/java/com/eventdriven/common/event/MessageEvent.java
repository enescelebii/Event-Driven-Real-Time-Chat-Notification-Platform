package com.eventdriven.common.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageEvent {
    private Long messageId;
    private Long senderId;
    private Long receiverId; // private chat
    private Long roomId;     // group chat
    private String action;   // SENT, EDITED, DELETED
}