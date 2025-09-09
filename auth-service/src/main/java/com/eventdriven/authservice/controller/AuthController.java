package com.eventdriven.authservice.controller;



import com.eventdriven.authservice.dto.*;
import com.eventdriven.authservice.entity.User;
import com.eventdriven.authservice.repository.UserRepository;
import com.eventdriven.authservice.security.JwtUtil;
import com.eventdriven.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthService authService;
    private final UserRepository userRepository;



    @PostMapping("/register")
    public AuthResponse register(@RequestBody UserDTO userDTO) {
        return authService.register(userDTO);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest.email(), loginRequest.password());
    }

    @PostMapping("/refresh-token")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        String email = jwtUtil.extractUsername(request.refreshToken());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRefreshToken().equals(request.refreshToken())) {
            throw new RuntimeException("Invalid refresh token");
        }

        return authService.refreshToken(user);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return authService.getUserById(id);
    }

    // Token üzerinden kullanıcı bilgisini döner
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7); // "Bearer " kısmını at
        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());

        return ResponseEntity.ok(userResponseDTO);
    }


}