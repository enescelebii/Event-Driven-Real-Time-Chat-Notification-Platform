package com.eventdriven.authservice.service;
import com.eventdriven.authservice.dto.FriendRequestResponseDTO;
import com.eventdriven.authservice.dto.UserResponseDTO;
import com.eventdriven.authservice.entity.FriendRequest;
import com.eventdriven.authservice.entity.User;
import com.eventdriven.authservice.mapper.UserMapper;
import com.eventdriven.authservice.repository.FriendRequestRepository;
import com.eventdriven.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public FriendRequestResponseDTO sendFriendRequest(String senderEmail, Long receiverId) {
        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // Sadece PENDING veya ACCEPTED istekleri engelle
        friendRequestRepository.findBySenderIdAndReceiverId(sender.getId(), receiver.getId())
                .filter(req -> req.getStatus() != FriendRequest.Status.REJECTED)
                .ifPresent(req -> {
                    throw new RuntimeException("Friend request already sent.");
                });

        FriendRequest request = FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .status(FriendRequest.Status.PENDING)
                .build();
        friendRequestRepository.save(request);

        return FriendRequestResponseDTO.fromEntity(request);
    }


    public FriendRequestResponseDTO acceptRequest(String receiverEmail, Long requestId) {
        User receiver = userRepository.findByEmail(receiverEmail)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!request.getReceiver().getId().equals(receiver.getId()))
            throw new RuntimeException("You cannot accept this request.");

        request.setStatus(FriendRequest.Status.ACCEPTED);
        friendRequestRepository.save(request);

        return FriendRequestResponseDTO.fromEntity(request);
    }

    public FriendRequestResponseDTO rejectRequest(String receiverEmail, Long requestId) {
        User receiver = userRepository.findByEmail(receiverEmail)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!request.getReceiver().getId().equals(receiver.getId()))
            throw new RuntimeException("You cannot reject this request.");

        request.setStatus(FriendRequest.Status.REJECTED);
        friendRequestRepository.save(request);

        return FriendRequestResponseDTO.fromEntity(request);
    }

    public List<FriendRequestResponseDTO> getReceivedRequests(String email) {
        User receiver = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        return friendRequestRepository.findByReceiverId(receiver.getId()).stream()
                .map(FriendRequestResponseDTO::fromEntity)
                .toList();
    }

    public List<FriendRequestResponseDTO> getSentRequests(String email) {
        User sender = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        return friendRequestRepository.findBySenderId(sender.getId()).stream()
                .map(FriendRequestResponseDTO::fromEntity)
                .toList();
    }

    /*
        ilişkiler iki taraflı oldugundan dolayı senkronizasyonun
         olması için arkadaşlık ilişkileri databasede tutuluyor
     */

    /*
    Kabul edilmiş ve kabul ettiklerimiz arkadaşları databaseden çekip topluyoruz
     */
    public List<UserResponseDTO> getFriends(Long userId) {
        return Stream.concat(
                        friendRequestRepository.findBySender_IdAndStatus(userId, FriendRequest.Status.ACCEPTED).stream()
                                .map(FriendRequest::getReceiver),
                        friendRequestRepository.findByReceiver_IdAndStatus(userId, FriendRequest.Status.ACCEPTED).stream()
                                .map(FriendRequest::getSender)
                )
                .map(userMapper::toResponseDTO) // her User’ı UserResponseDTO’ya çevir
                .toList(); // final listeyi oluştur
    }
    /*
        id uzerinde data base sorgusu atma
     */
    public boolean areFriends(Long userId1, Long userId2) {
        return friendRequestRepository.existsBySender_IdAndReceiver_IdAndStatus(userId1, userId2, FriendRequest.Status.ACCEPTED) ||
                friendRequestRepository.existsBySender_IdAndReceiver_IdAndStatus(userId2, userId1, FriendRequest.Status.ACCEPTED);
    }

    public Long getUserIdByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }
}
