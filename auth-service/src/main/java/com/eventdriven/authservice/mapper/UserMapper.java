package com.eventdriven.authservice.mapper;

import com.eventdriven.authservice.dto.UserDTO;
import com.eventdriven.authservice.dto.UserResponseDTO;
import com.eventdriven.authservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO dto);

    UserResponseDTO toResponseDTO(User user);
}