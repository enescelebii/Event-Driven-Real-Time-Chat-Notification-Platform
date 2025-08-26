package com.eventdriven.authservice.dto;

public record AuthResponse(
        UserDTO user,
        String token
) {}