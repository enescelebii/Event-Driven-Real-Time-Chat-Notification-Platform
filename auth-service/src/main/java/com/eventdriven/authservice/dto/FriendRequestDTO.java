package com.eventdriven.authservice.dto;

import com.eventdriven.authservice.entity.FriendRequest;
import lombok.Data;

@Data
public class FriendRequestDTO {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String status;

    public static FriendRequestDTO fromEntity(FriendRequest request) {
        FriendRequestDTO dto = new FriendRequestDTO();
        dto.setId(request.getId());
        dto.setSenderId(request.getSender().getId());
        dto.setReceiverId(request.getReceiver().getId());
        dto.setStatus(request.getStatus().name());
        return dto;
    }
}