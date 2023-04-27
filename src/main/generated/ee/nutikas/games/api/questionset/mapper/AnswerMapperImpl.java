package ee.nutikas.games.api.questionset.mapper;

import ee.nutikas.games.api.questionset.dto.AnswerDTO;
import ee.nutikas.games.api.questionset.dto.AnswerRequest;
import ee.nutikas.games.api.questionset.model.AnswerModel;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-22T22:00:12+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Eclipse Adoptium)"
)
public class AnswerMapperImpl implements AnswerMapper {

    @Override
    public AnswerModel toModel(AnswerRequest request) {
        if ( request == null ) {
            return null;
        }

        AnswerModel answerModel = new AnswerModel();

        answerModel.setValue( request.getText() );
        answerModel.setIsCorrect( request.getIsCorrect() );

        return answerModel;
    }

    @Override
    public AnswerDTO toDTO(AnswerModel model) {
        if ( model == null ) {
            return null;
        }

        AnswerDTO answerDTO = new AnswerDTO();

        answerDTO.setId( model.getId() );
        answerDTO.setIsCorrect( model.getIsCorrect() );

        return answerDTO;
    }
}
