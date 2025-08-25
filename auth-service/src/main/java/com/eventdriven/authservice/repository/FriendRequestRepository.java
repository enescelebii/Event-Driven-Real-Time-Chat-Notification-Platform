package com.eventdriven.authservice.repository;

import com.eventdriven.authservice.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByReceiverId(Long receiverId);
}