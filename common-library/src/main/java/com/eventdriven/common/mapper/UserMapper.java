package com.eventdriven.common.mapper;

import com.eventdriven.common.dto.UserDTO;
import com.eventdriven.common.entity.UserStub;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(UserStub user);
    UserStub toEntity(UserDTO dto);
}