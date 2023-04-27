package ee.nutikas.games.api.game.mapper;

import ee.nutikas.games.api.game.dto.GameRequest;
import ee.nutikas.games.api.game.dto.GameResponse;
import ee.nutikas.games.api.game.model.GameModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GameMapper {

    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    @Mapping(source = "maxPlays", target = "allowedPlayTimes")
    @Mapping(source = "time", target = "gameTime")
    @Mapping(source = "type", target = "gameType")
    GameModel toModel(GameRequest request);

    @Mapping(source = "allowedPlayTimes", target = "maxPlays")
    @Mapping(source = "gameTime", target = "time")
    @Mapping(source = "gameType", target = "type")
    @Mapping(source = "questionSet.name", target = "questionSetName")
    GameResponse toResponse(GameModel model);

    List<GameResponse> toResponseList(List<GameModel> models);

}
