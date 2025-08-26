package com.eventdriven.authservice.controller;

import com.eventdriven.authservice.dto.FriendRequestDTO;
import com.eventdriven.authservice.entity.User;
import com.eventdriven.authservice.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    // ✅ Arkadaşlık isteği gönder
    @PostMapping("/send/{id}")
    public FriendRequestDTO sendFriendRequest(@RequestParam Long receiverId, Authentication authentication) {
        String senderEmail = authentication.getName(); // Token'dan email alıyoruz
        return friendRequestService.sendFriendRequest(senderEmail, receiverId);
    }

    // ✅ İsteği kabul et
    @PostMapping("/{requestId}/accept")
    public FriendRequestDTO acceptRequest(@PathVariable Long requestId, Authentication authentication) {
        String userEmail = authentication.getName();
        return friendRequestService.acceptRequest(userEmail,requestId);
    }

    // ✅ İsteği reddet
    @PostMapping("/{requestId}/reject")
    public FriendRequestDTO rejectRequest(@PathVariable Long requestId, Authentication authentication) {
        String userEmail = authentication.getName();
        return friendRequestService.rejectRequest(userEmail,requestId);
    }

    // ✅ Bana gelen istekler
    @GetMapping("/received")
    public List<FriendRequestDTO> getReceivedRequests(Authentication authentication) {
        String userEmail = authentication.getName();
        return friendRequestService.getReceivedRequests(userEmail);
    }

    // ✅ Benim gönderdiğim istekler
    @GetMapping("/sent")
    public List<FriendRequestDTO> getSentRequests(Authentication authentication) {
        String userEmail = authentication.getName();
        return friendRequestService.getSentRequests(userEmail);
    }

    @GetMapping("/friends/{id}")
    public List<User>  getFriends(@PathVariable Long id) {
        return friendRequestService.getFriends(id);
    }

    @GetMapping("/are-friends")
    public boolean areFriends(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        return friendRequestService.areFriends(user1Id, user2Id);
    }


}
