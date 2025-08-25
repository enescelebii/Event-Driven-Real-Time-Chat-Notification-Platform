package com.eventdriven.common.mapper;

import com.eventdriven.common.dto.UserDTO;
import com.eventdriven.chatauthservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}