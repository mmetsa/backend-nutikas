package com.nutikas.backendnutikas.mapper;

import com.nutikas.backendnutikas.dto.UserDTO;
import com.nutikas.backendnutikas.dto.UserResponse;
import com.nutikas.backendnutikas.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "username", target = "email")
    UserDTO modelToDTO(UserModel userModel);

    UserResponse dtoToResponse(UserDTO dto);
}
