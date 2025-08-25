package com.eventdriven.common.entity;

import lombok.Data;

import java.util.Set;

@Data
public class ChatRoomStub {
    private Long roomId;
    private String roomName;
    private Set<Long> participantIds;
}
