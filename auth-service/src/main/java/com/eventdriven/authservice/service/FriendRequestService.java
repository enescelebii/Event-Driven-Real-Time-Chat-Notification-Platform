package com.eventdriven.authservice.service;
import com.eventdriven.authservice.dto.FriendRequestDTO;
import com.eventdriven.authservice.entity.FriendRequest;
import com.eventdriven.authservice.entity.User;
import com.eventdriven.authservice.repository.FriendRequestRepository;
import com.eventdriven.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;


    public FriendRequestDTO acceptRequest(String receiverEmail, Long requestId) {
        User receiver = userRepository.findByEmail(receiverEmail)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!request.getReceiver().getId().equals(receiver.getId())) {
            throw new RuntimeException("You cannot accept this request.");
        }

        request.setStatus(FriendRequest.Status.ACCEPTED);
        friendRequestRepository.save(request);
        return FriendRequestDTO.fromEntity(request);
    }

    public FriendRequestDTO rejectRequest(String receiverEmail, Long requestId) {
        User receiver = userRepository.findByEmail(receiverEmail)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!request.getReceiver().getId().equals(receiver.getId())) {
            throw new RuntimeException("You cannot reject this request.");
        }

        request.setStatus(FriendRequest.Status.REJECTED);
        friendRequestRepository.save(request);
        return FriendRequestDTO.fromEntity(request);
    }

    public List<FriendRequestDTO> getReceivedRequests(String email) {
        User receiver = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        return friendRequestRepository.findByReceiverId(receiver.getId()).stream()
                .map(FriendRequestDTO::fromEntity)
                .toList();
    }

    public List<FriendRequestDTO> getSentRequests(String email) {
        User sender = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        return friendRequestRepository.findBySenderId(sender.getId()).stream()
                .map(FriendRequestDTO::fromEntity)
                .toList();
    }

    /*
        ilişkiler iki taraflı oldugundan dolayı senkronizasyonun
         olması için arkadaşlık ilişkileri databasede tutuluyor
     */

    /*
    Kabul edilmiş ve kabul ettiklerimiz arkadaşları databaseden çekip topluyoruz
     */
    public List<User> getFriends(Long userId) {
        List<FriendRequest> acceptedSent = friendRequestRepository.findBySender_IdAndStatus(userId, FriendRequest.Status.ACCEPTED);
        List<FriendRequest> acceptedReceived = friendRequestRepository.findByReceiver_IdAndStatus(userId, FriendRequest.Status.ACCEPTED);

        List<User> friends = acceptedSent.stream()
                .map(FriendRequest::getReceiver)
                .collect(Collectors.toList());

        friends.addAll(acceptedReceived.stream()
                .map(FriendRequest::getSender)
                .toList());

        return friends;
    }
    /*
        id uzerinde data base sorgusu atma
     */
    public boolean areFriends(Long userId1, Long userId2) {
        return friendRequestRepository.existsBySender_IdAndReceiver_IdAndStatus(userId1, userId2, FriendRequest.Status.ACCEPTED) ||
                friendRequestRepository.existsBySender_IdAndReceiver_IdAndStatus(userId2, userId1, FriendRequest.Status.ACCEPTED);
    }

    public FriendRequestDTO sendFriendRequest(String senderEmail, Long receiverId) {
        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // Zaten istek varsa tekrar ekleme
        boolean exists = friendRequestRepository
                .findBySenderIdAndReceiverId(sender.getId(), receiver.getId())
                .isPresent();
        if (exists) {
            throw new RuntimeException("Friend request already sent.");
        }

        FriendRequest request = FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .status(FriendRequest.Status.PENDING)
                .build();

        friendRequestRepository.save(request);

        return FriendRequestDTO.fromEntity(request);
    }
}
