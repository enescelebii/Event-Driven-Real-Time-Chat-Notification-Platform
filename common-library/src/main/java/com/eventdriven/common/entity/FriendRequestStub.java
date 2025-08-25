package com.eventdriven.common.entity;

import lombok.Data;

@Data
public class FriendRequestStub {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String status; // PENDING, ACCEPTED, REJECTED
}
