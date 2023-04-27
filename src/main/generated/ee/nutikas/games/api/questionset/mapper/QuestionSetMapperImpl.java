package ee.nutikas.games.api.questionset.mapper;

import ee.nutikas.games.api.questionset.dto.QuestionDTO;
import ee.nutikas.games.api.questionset.dto.QuestionRequest;
import ee.nutikas.games.api.questionset.dto.QuestionSetDTO;
import ee.nutikas.games.api.questionset.dto.QuestionSetRequest;
import ee.nutikas.games.api.questionset.model.QuestionModel;
import ee.nutikas.games.api.questionset.model.QuestionSetModel;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-22T22:00:12+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Eclipse Adoptium)"
)
public class QuestionSetMapperImpl implements QuestionSetMapper {

    private final QuestionMapper questionMapper = QuestionMapper.INSTANCE;
    private final DatatypeFactory datatypeFactory;

    public QuestionSetMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public QuestionSetModel toModel(QuestionSetRequest request) {
        if ( request == null ) {
            return null;
        }

        QuestionSetModel questionSetModel = new QuestionSetModel();

        questionSetModel.setVisibility( request.getVisibility() );
        questionSetModel.setName( request.getName() );
        questionSetModel.setQuestions( questionRequestListToQuestionModelList( request.getQuestions() ) );

        return questionSetModel;
    }

    @Override
    public QuestionSetDTO toDTO(QuestionSetModel model) {
        if ( model == null ) {
            return null;
        }

        QuestionSetDTO questionSetDTO = new QuestionSetDTO();

        questionSetDTO.setQuestions( questionModelListToQuestionDTOList( model.getQuestions() ) );
        questionSetDTO.setDate( xmlGregorianCalendarToLocalDateTime( dateToXmlGregorianCalendar( model.getCreatedAt() ) ) );
        questionSetDTO.setAmount( mapAmount( model.getQuestions() ) );
        questionSetDTO.setId( model.getId() );
        questionSetDTO.setOwnerId( model.getOwnerId() );
        questionSetDTO.setName( model.getName() );
        questionSetDTO.setVisibility( model.getVisibility() );

        return questionSetDTO;
    }

    private XMLGregorianCalendar dateToXmlGregorianCalendar( Date date ) {
        if ( date == null ) {
            return null;
        }

        GregorianCalendar c = new GregorianCalendar();
        c.setTime( date );
        return datatypeFactory.newXMLGregorianCalendar( c );
    }

    private static LocalDateTime xmlGregorianCalendarToLocalDateTime( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        if ( xcal.getYear() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMonth() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getDay() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getHour() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMinute() != DatatypeConstants.FIELD_UNDEFINED
        ) {
            if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED
                && xcal.getMillisecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    Duration.ofMillis( xcal.getMillisecond() ).getNano()
                );
            }
            else if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond()
                );
            }
            else {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute()
                );
            }
        }
        return null;
    }

    protected List<QuestionModel> questionRequestListToQuestionModelList(List<QuestionRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<QuestionModel> list1 = new ArrayList<QuestionModel>( list.size() );
        for ( QuestionRequest questionRequest : list ) {
            list1.add( questionMapper.toModel( questionRequest ) );
        }

        return list1;
    }

    protected List<QuestionDTO> questionModelListToQuestionDTOList(List<QuestionModel> list) {
        if ( list == null ) {
            return null;
        }

        List<QuestionDTO> list1 = new ArrayList<QuestionDTO>( list.size() );
        for ( QuestionModel questionModel : list ) {
            list1.add( toQuestionDTOWithoutQuestionSet( questionModel ) );
        }

        return list1;
    }
}
