package ee.nutikas.games.api.questionset.mapper;

import ee.nutikas.games.api.questionset.dto.AnswerDTO;
import ee.nutikas.games.api.questionset.dto.QuestionDTO;
import ee.nutikas.games.api.questionset.dto.QuestionSetDTO;
import ee.nutikas.games.api.questionset.dto.QuestionSetRequest;
import ee.nutikas.games.api.questionset.model.QuestionModel;
import ee.nutikas.games.api.questionset.model.QuestionSetModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = QuestionMapper.class)
public interface QuestionSetMapper {

    QuestionSetMapper INSTANCE = Mappers.getMapper(QuestionSetMapper.class);

    QuestionSetModel toModel(QuestionSetRequest request);
    @Mapping(target = "questions", qualifiedByName = "toQuestionDTOWithoutQuestionSet")
    @Mapping(source = "createdAt", target = "date")
    @Mapping(source = "questions", target = "amount", qualifiedByName = "mapAmount")
    QuestionSetDTO toDTO(QuestionSetModel model);

    @Named("mapAmount")
    default Integer mapAmount(List<QuestionModel> questions) {
        return questions.size();
    }

    @Named("toQuestionDTOWithoutQuestionSet")
    default QuestionDTO toQuestionDTOWithoutQuestionSet(QuestionModel model) {
        if (model == null) {
            return null;
        }

        var response = new QuestionDTO();
        response.setId(model.getId());
        response.setText(model.getText());
        response.setAnswerType(model.getAnswerType());
        response.setAnswers(model.getAnswers().stream()
                .map(answerModel -> {
                    var answerResponse = new AnswerDTO();
                    answerResponse.setId(answerModel.getId());
                    answerResponse.setText(answerModel.getValue());
                    answerResponse.setIsCorrect(answerModel.getIsCorrect());
                    return answerResponse;
                }).toList());

        return response;
    }
}
