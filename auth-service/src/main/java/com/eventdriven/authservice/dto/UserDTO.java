// UserDTO.java
package com.eventdriven.authservice.dto;

public record UserDTO(
        Long id,
        String username,
        String password,
        String email,
        String token // JWT token login sonrası dönecek
) {}
