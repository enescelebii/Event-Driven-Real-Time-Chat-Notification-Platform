package com.eventdriven.authservice.dto;


public record SendFriendRequest(
        Long senderId,
        Long receiverId
) {}