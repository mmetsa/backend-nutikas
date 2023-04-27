package ee.nutikas.games.api.questionset.mapper;

import ee.nutikas.games.api.questionset.dto.AnswerDTO;
import ee.nutikas.games.api.questionset.dto.AnswerRequest;
import ee.nutikas.games.api.questionset.model.AnswerModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AnswerMapper {
    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

    @Mapping(source = "text", target = "value")
    AnswerModel toModel(AnswerRequest request);

    AnswerDTO toDTO(AnswerModel model);
}
