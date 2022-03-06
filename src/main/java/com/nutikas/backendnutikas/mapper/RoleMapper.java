package com.nutikas.backendnutikas.mapper;

import com.nutikas.backendnutikas.dto.RoleResponse;
import com.nutikas.backendnutikas.model.RoleModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleResponse modelToResponse(RoleModel model);

}
