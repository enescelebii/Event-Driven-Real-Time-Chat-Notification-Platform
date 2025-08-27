package com.eventdriven.authservice.dto;

import com.eventdriven.authservice.entity.FriendRequest;

public record FriendRequestResponseDTO(
        Long id,
        UserResponseDTO sender,
        UserResponseDTO receiver,
        String status
) {
    public static FriendRequestResponseDTO fromEntity(FriendRequest request) {
        return new FriendRequestResponseDTO(
                request.getId(),
                new UserResponseDTO(
                        request.getSender().getId(),
                        request.getSender().getUsername(),
                        request.getSender().getEmail()
                ),
                new UserResponseDTO(
                        request.getReceiver().getId(),
                        request.getReceiver().getUsername(),
                        request.getReceiver().getEmail()
                ),
                request.getStatus().name()
        );
    }
}
