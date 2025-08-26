package com.eventdriven.authservice.controller;


import com.eventdriven.authservice.dto.LoginRequest;
import com.eventdriven.authservice.dto.UserDTO;
import com.eventdriven.authservice.security.JwtUtil;
import com.eventdriven.authservice.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthService authService;

    // DTO olarak password dönülmüyor
    @Data
    @AllArgsConstructor
    public static class AuthResponse {
        private Long id;
        private String username;
        private String email;
        private String token;
    }


    @PostMapping("/register")
    public AuthResponse register(@RequestBody UserDTO userDTO) {
        UserDTO registeredUser = authService.register(userDTO);
        String token = jwtUtil.generateToken(registeredUser.email());
        return new AuthResponse(registeredUser.id(), registeredUser.username(), registeredUser.email(), token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        UserDTO userDTO = authService.login(loginRequest.email(), loginRequest.password());
        String token = jwtUtil.generateToken(userDTO.email());
        return new AuthResponse(userDTO.id(), userDTO.username(), userDTO.email(), token);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return authService.getUserById(id);
    }


}
