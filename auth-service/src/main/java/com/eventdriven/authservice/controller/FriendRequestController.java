package com.eventdriven.authservice.controller;

import com.eventdriven.authservice.dto.*;
import com.eventdriven.authservice.service.AuthService;
import com.eventdriven.authservice.service.FriendRequestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;
    private final AuthService authService;


    //  Arkadaşlık isteği gönder
    @PostMapping("/send/{receiverId}")
    public FriendRequestResponseDTO sendFriendRequest(@PathVariable Long receiverId, Authentication authentication) {
        String senderEmail = authentication.getName();
        return friendRequestService.sendFriendRequest(senderEmail, receiverId);
    }

    //  İsteği kabul et
    @PostMapping("/{requestId}/accept")
    public FriendRequestResponseDTO acceptRequest(@PathVariable Long requestId, Authentication authentication) {
        String userEmail = authentication.getName();
        return friendRequestService.acceptRequest(userEmail,requestId);
    }

    //  İsteği reddet
    @PostMapping("/{requestId}/reject")
    public FriendRequestResponseDTO rejectRequest(@PathVariable Long requestId, Authentication authentication) {
        String userEmail = authentication.getName();
        return friendRequestService.rejectRequest(userEmail,requestId);
    }

    //  Bana gelen istekler
    @GetMapping("/received")
    public List<FriendRequestResponseDTO> getReceivedRequests(Authentication authentication) {
        String userEmail = authentication.getName();
        return friendRequestService.getReceivedRequests(userEmail);
    }

    //  Benim gönderdiğim istekler
    @GetMapping("/sent")
    public List<FriendRequestResponseDTO> getSentRequests(Authentication authentication) {
        String userEmail = authentication.getName();
        return friendRequestService.getSentRequests(userEmail);
    }

    // Arkadaş listem
    @GetMapping("/list")
    public List<UserResponseDTO> getFriends(Authentication authentication) {
        String userEmail = authentication.getName();

        UserDTO userDTO = authService.getUserByEmail(userEmail); // AuthService’de getUserByEmail metodu olmalı
        Long userId = userDTO.id();

        return friendRequestService.getFriends(userId);
    }

    // Arkadaş mıyız
    @GetMapping("/are-friends")
    public boolean areFriends(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        return friendRequestService.areFriends(user1Id, user2Id);
    }


}
