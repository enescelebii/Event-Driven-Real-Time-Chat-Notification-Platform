package com.eventdriven.common.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String avatarUrl;
    private Set<String> roles;
}

