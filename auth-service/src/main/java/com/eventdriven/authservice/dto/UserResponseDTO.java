package com.eventdriven.authservice.dto;

public record UserResponseDTO(
        Long id,
        String username,
        String email
) {}
