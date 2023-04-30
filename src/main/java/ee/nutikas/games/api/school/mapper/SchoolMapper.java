package ee.nutikas.games.api.school.mapper;

import ee.nutikas.games.api.school.dto.SchoolRequest;
import ee.nutikas.games.api.school.dto.SchoolResponse;
import ee.nutikas.games.api.school.model.SchoolModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ClassMapper.class})
public interface SchoolMapper {

    SchoolMapper MAPPER = Mappers.getMapper(SchoolMapper.class);

    SchoolResponse toResponse(SchoolModel model);
    List<SchoolResponse> toResponseList(List<SchoolModel> models);

    @Mapping(source = "schoolEmail", target = "email")
    SchoolModel toModel(SchoolRequest request);
}
