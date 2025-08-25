package com.eventdriven.common.mapper;

import com.eventdriven.common.dto.FriendRequestDTO;
import com.eventdriven.common.entity.FriendRequestStub;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FriendRequestMapper {
    FriendRequestDTO toDTO(FriendRequestStub friendRequest);
    FriendRequestStub toEntity(FriendRequestDTO friendRequestDTO);
}
