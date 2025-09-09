package com.eventdriven.authservice.service;


import com.eventdriven.authservice.dto.UserResponseDTO;
import com.eventdriven.authservice.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void notifyFriendAdded(Long receiverId, User newFriend) {
        UserResponseDTO dto = new UserResponseDTO(
                newFriend.getId(),
                newFriend.getUsername(),
                newFriend.getEmail()
        );
        messagingTemplate.convertAndSend("/topic/friends/" + receiverId, dto);}
}
