package ee.nutikas.games.api.questionset.mapper;

import ee.nutikas.games.api.questionset.dto.AnswerDTO;
import ee.nutikas.games.api.questionset.dto.AnswerRequest;
import ee.nutikas.games.api.questionset.dto.QuestionDTO;
import ee.nutikas.games.api.questionset.dto.QuestionRequest;
import ee.nutikas.games.api.questionset.dto.QuestionSetDTO;
import ee.nutikas.games.api.questionset.model.AnswerModel;
import ee.nutikas.games.api.questionset.model.QuestionModel;
import ee.nutikas.games.api.questionset.model.QuestionSetModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-22T22:00:12+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Eclipse Adoptium)"
)
public class QuestionMapperImpl implements QuestionMapper {

    private final AnswerMapper answerMapper = AnswerMapper.INSTANCE;

    @Override
    public QuestionModel toModel(QuestionRequest request) {
        if ( request == null ) {
            return null;
        }

        QuestionModel questionModel = new QuestionModel();

        questionModel.setText( request.getQuestionText() );
        questionModel.setAnswers( answerRequestListToAnswerModelList( request.getAnswers() ) );
        questionModel.setAnswerType( request.getAnswerType() );

        return questionModel;
    }

    @Override
    public QuestionDTO toDTO(QuestionModel model) {
        if ( model == null ) {
            return null;
        }

        QuestionDTO questionDTO = new QuestionDTO();

        questionDTO.setId( model.getId() );
        questionDTO.setQuestionSet( questionSetModelToQuestionSetDTO( model.getQuestionSet() ) );
        questionDTO.setText( model.getText() );
        questionDTO.setAnswerType( model.getAnswerType() );
        questionDTO.setAnswers( answerModelListToAnswerDTOList( model.getAnswers() ) );

        return questionDTO;
    }

    protected List<AnswerModel> answerRequestListToAnswerModelList(List<AnswerRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<AnswerModel> list1 = new ArrayList<AnswerModel>( list.size() );
        for ( AnswerRequest answerRequest : list ) {
            list1.add( answerMapper.toModel( answerRequest ) );
        }

        return list1;
    }

    protected List<QuestionDTO> questionModelListToQuestionDTOList(List<QuestionModel> list) {
        if ( list == null ) {
            return null;
        }

        List<QuestionDTO> list1 = new ArrayList<QuestionDTO>( list.size() );
        for ( QuestionModel questionModel : list ) {
            list1.add( toDTO( questionModel ) );
        }

        return list1;
    }

    protected QuestionSetDTO questionSetModelToQuestionSetDTO(QuestionSetModel questionSetModel) {
        if ( questionSetModel == null ) {
            return null;
        }

        QuestionSetDTO questionSetDTO = new QuestionSetDTO();

        questionSetDTO.setId( questionSetModel.getId() );
        questionSetDTO.setOwnerId( questionSetModel.getOwnerId() );
        questionSetDTO.setName( questionSetModel.getName() );
        questionSetDTO.setQuestions( questionModelListToQuestionDTOList( questionSetModel.getQuestions() ) );
        questionSetDTO.setVisibility( questionSetModel.getVisibility() );

        return questionSetDTO;
    }

    protected List<AnswerDTO> answerModelListToAnswerDTOList(List<AnswerModel> list) {
        if ( list == null ) {
            return null;
        }

        List<AnswerDTO> list1 = new ArrayList<AnswerDTO>( list.size() );
        for ( AnswerModel answerModel : list ) {
            list1.add( answerMapper.toDTO( answerModel ) );
        }

        return list1;
    }
}
