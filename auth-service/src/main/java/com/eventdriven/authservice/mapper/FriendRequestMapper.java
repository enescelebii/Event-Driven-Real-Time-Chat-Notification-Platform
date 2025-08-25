package com.eventdriven.authservice.mapper;


import com.eventdriven.authservice.dto.FriendRequestDTO;
import com.eventdriven.authservice.entity.FriendRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FriendRequestMapper {

    FriendRequestDTO toDTO(FriendRequest entity);

    FriendRequest toEntity(FriendRequestDTO dto);
}