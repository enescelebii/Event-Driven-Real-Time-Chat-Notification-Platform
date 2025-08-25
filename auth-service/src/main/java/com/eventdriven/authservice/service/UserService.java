package com.eventdriven.authservice.service;



import com.eventdriven.authservice.entity.User;
import com.eventdriven.authservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

}
