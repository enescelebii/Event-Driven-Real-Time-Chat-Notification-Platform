package com.eventdriven.authservice.dto;

public record FriendRequestDTO(
        Long id,
        Long senderId,
        Long receiverId,
        String status // PENDING, ACCEPTED, REJECTED
) {}