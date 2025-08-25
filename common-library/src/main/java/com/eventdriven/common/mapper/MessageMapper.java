package com.eventdriven.common.mapper;

import com.eventdriven.common.dto.MessageDTO;

import com.eventdriven.common.entity.MessageStub;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageDTO toDTO(MessageStub message);
    MessageStub toEntity(MessageDTO messageDTO);
}