package ee.nutikas.games.api.school.mapper;

import ee.nutikas.games.api.school.dto.ClassResponse;
import ee.nutikas.games.api.school.model.ClassModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ClassMapper {

    ClassMapper INSTANCE = Mappers.getMapper(ClassMapper.class);

    ClassResponse toResponse(ClassModel model);

    List<ClassResponse> toResponseList(List<ClassModel> models);


}
