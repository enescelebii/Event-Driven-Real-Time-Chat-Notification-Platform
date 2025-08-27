package com.eventdriven.authservice.service;

import com.eventdriven.authservice.dto.AuthResponse;
import com.eventdriven.authservice.dto.UserDTO;
import com.eventdriven.authservice.entity.User;
import com.eventdriven.authservice.mapper.UserMapper;
import com.eventdriven.authservice.repository.UserRepository;
import com.eventdriven.authservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Register
    public AuthResponse register(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        String accessToken = jwtUtil.generateToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new AuthResponse(accessToken, refreshToken);
    }

    // Login
    public AuthResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtUtil.generateToken(email);
        String refreshToken = jwtUtil.generateRefreshToken(email);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new AuthResponse(accessToken, refreshToken);
    }

    // Refresh token
    public AuthResponse refreshToken(User user) {
        String newAccessToken = jwtUtil.generateToken(user.getEmail());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getEmail());
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);
        return new AuthResponse(newAccessToken, newRefreshToken);
    }

    // Kullanıcıyı id ile getir
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }
}

 /*// Register
    public UserDTO register(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.email()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // şifreyi encode et
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    // Login
    public UserDTO login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return userMapper.toDTO(user);
    }*/