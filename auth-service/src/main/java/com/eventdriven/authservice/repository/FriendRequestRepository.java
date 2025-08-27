package com.eventdriven.authservice.repository;

import com.eventdriven.authservice.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByReceiverId(Long receiverId);
    List<FriendRequest> findBySenderId(Long senderId);
    List<FriendRequest> findByReceiver_Id(Long receiverId);
    List<FriendRequest> findBySender_Id(Long senderId);

    boolean existsBySender_IdAndReceiver_IdAndStatus(Long userId1, Long userId2, FriendRequest.Status status);

    List<FriendRequest> findByReceiver_IdAndStatus(Long userId, FriendRequest.Status status);

    List<FriendRequest> findBySender_IdAndStatus(Long userId, FriendRequest.Status status);

    Optional<FriendRequest> findBySenderIdAndReceiverId(Long id, Long id1);
}