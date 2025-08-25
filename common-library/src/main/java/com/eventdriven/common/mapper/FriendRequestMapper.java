package com.eventdriven.common.mapper;

import com.eventdriven.common.dto.FriendRequestDTO;
import com.eventdriven.chatauthservice.entity.FriendRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FriendRequestMapper {
    FriendRequestDTO toDTO(FriendRequest friendRequest);
    FriendRequest toEntity(FriendRequestDTO friendRequestDTO);
}
