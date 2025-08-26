package com.eventdriven.authservice.dto;

import com.eventdriven.authservice.entity.FriendRequest.Status;

public record RespondFriendRequest(
        Long requestId,
        Status action
) {}