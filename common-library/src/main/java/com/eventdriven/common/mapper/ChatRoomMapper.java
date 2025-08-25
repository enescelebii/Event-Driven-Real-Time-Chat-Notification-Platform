package com.eventdriven.common.mapper;

import com.eventdriven.common.dto.ChatRoomDTO;
import com.eventdriven.common.entity.ChatRoomStub;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {
    ChatRoomDTO toDTO(ChatRoomStub chatRoom);
    ChatRoomStub toEntity(ChatRoomDTO chatRoomDTO);
}
