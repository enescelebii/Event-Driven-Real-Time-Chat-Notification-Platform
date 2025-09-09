package com.eventdriven.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendAddedNotification {
    private Long userId; // yeni eklenen arkadaşın id'si
    private String username;
}
