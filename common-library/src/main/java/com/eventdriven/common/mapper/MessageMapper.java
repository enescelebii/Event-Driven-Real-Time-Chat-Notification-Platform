package com.eventdriven.common.mapper;

import com.eventdriven.common.dto.MessageDTO;
import com.eventdriven.chatmessageservice.entity.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageDTO toDTO(Message message);
    Message toEntity(MessageDTO messageDTO);
}