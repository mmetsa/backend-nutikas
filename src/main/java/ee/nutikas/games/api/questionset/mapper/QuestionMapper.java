package ee.nutikas.games.api.questionset.mapper;

import ee.nutikas.games.api.questionset.dto.QuestionDTO;
import ee.nutikas.games.api.questionset.dto.QuestionRequest;
import ee.nutikas.games.api.questionset.model.QuestionModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(uses = AnswerMapper.class)
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    @Mapping(source = "questionText", target = "text")
    QuestionModel toModel(QuestionRequest request);

    QuestionDTO toDTO(QuestionModel model);
}
