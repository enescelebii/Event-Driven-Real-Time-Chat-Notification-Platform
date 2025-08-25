package com.eventdriven.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendRequestDTO {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String status; // PENDING, ACCEPTED, REJECTED
}
