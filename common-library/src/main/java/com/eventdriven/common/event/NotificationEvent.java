package com.eventdriven.common.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationEvent {
    private Long userId;
    private String type;    // FRIEND_REQUEST, SYSTEM_ALERT, MESSAGE
    private String message; // notification content
}