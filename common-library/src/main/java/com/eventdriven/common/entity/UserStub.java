package com.eventdriven.common.entity;

import lombok.Data;

import java.util.Set;

@Data
public class UserStub {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String avatarUrl;
    private Set<String> roles;
}