// UserDTO.java
package com.eventdriven.authservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserDTO(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
        String username,
        String password,
        String email
) {}
