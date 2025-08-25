package com.eventdriven.common.mapper;

import com.eventdriven.common.dto.ChatRoomDTO;
import com.eventdriven.chatmessageservice.entity.ChatRoom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {
    ChatRoomDTO toDTO(ChatRoom chatRoom);
    ChatRoom toEntity(ChatRoomDTO chatRoomDTO);
}
