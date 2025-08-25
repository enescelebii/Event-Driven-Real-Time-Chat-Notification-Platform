package com.eventdriven.common.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEvent {
    private Long userId;
    private String action; // CREATED, UPDATED, DELETED
}