package ee.nutikas.games.api.game.mapper;

import ee.nutikas.games.api.game.dto.GameRequest;
import ee.nutikas.games.api.game.dto.GameResponse;
import ee.nutikas.games.api.game.model.GameModel;
import ee.nutikas.games.api.questionset.model.QuestionSetModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-22T22:00:12+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Eclipse Adoptium)"
)
public class GameMapperImpl implements GameMapper {

    @Override
    public GameModel toModel(GameRequest request) {
        if ( request == null ) {
            return null;
        }

        GameModel gameModel = new GameModel();

        gameModel.setAllowedPlayTimes( request.getMaxPlays() );
        gameModel.setGameTime( request.getTime() );
        gameModel.setGameType( request.getType() );
        gameModel.setClassId( request.getClassId() );
        gameModel.setName( request.getName() );
        gameModel.setStartingTime( request.getStartingTime() );
        gameModel.setEndingTime( request.getEndingTime() );
        gameModel.setHintsEnabled( request.getHintsEnabled() );
        gameModel.setPointPenalty( request.getPointPenalty() );
        gameModel.setPointsPerQuestion( request.getPointsPerQuestion() );
        gameModel.setPowerUpsEnabled( request.getPowerUpsEnabled() );

        return gameModel;
    }

    @Override
    public GameResponse toResponse(GameModel model) {
        if ( model == null ) {
            return null;
        }

        GameResponse gameResponse = new GameResponse();

        gameResponse.setMaxPlays( model.getAllowedPlayTimes() );
        gameResponse.setTime( model.getGameTime() );
        gameResponse.setType( model.getGameType() );
        gameResponse.setQuestionSetName( modelQuestionSetName( model ) );
        gameResponse.setId( model.getId() );
        gameResponse.setClassId( model.getClassId() );
        gameResponse.setName( model.getName() );
        gameResponse.setStartingTime( model.getStartingTime() );
        gameResponse.setEndingTime( model.getEndingTime() );
        gameResponse.setHintsEnabled( model.getHintsEnabled() );
        gameResponse.setPointPenalty( model.getPointPenalty() );
        gameResponse.setPointsPerQuestion( model.getPointsPerQuestion() );
        gameResponse.setPowerUpsEnabled( model.getPowerUpsEnabled() );
        gameResponse.setCode( model.getCode() );

        return gameResponse;
    }

    @Override
    public List<GameResponse> toResponseList(List<GameModel> models) {
        if ( models == null ) {
            return null;
        }

        List<GameResponse> list = new ArrayList<GameResponse>( models.size() );
        for ( GameModel gameModel : models ) {
            list.add( toResponse( gameModel ) );
        }

        return list;
    }

    private String modelQuestionSetName(GameModel gameModel) {
        if ( gameModel == null ) {
            return null;
        }
        QuestionSetModel questionSet = gameModel.getQuestionSet();
        if ( questionSet == null ) {
            return null;
        }
        String name = questionSet.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
