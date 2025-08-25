package com.eventdriven.authservice.dto;

public record RespondFriendRequest(
        Long requestId,
        String action // ACCEPT or REJECT
) {}