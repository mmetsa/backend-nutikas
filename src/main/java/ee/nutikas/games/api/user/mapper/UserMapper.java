package ee.nutikas.games.api.user.mapper;

import ee.nutikas.games.api.auth.model.UserModel;
import ee.nutikas.games.api.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toResponse(UserModel model);

    List<UserResponse> toResponseList(List<UserModel> models);

}
