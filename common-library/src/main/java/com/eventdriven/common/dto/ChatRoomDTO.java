package com.eventdriven.common.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class ChatRoomDTO {
    private Long roomId;
    private String roomName;
    private Set<Long> participantIds;
}