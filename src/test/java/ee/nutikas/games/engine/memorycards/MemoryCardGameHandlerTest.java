package ee.nutikas.games.engine.memorycards;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.nutikas.games.engine.GameState;
import ee.nutikas.games.engine.memorycards.dto.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MemoryCardGameHandlerTest {

    private static final Long PLAYER_ID = 3L;
    private static final Long QUESTION_ID = 1L;
    private static final String QUESTION_TEXT = "What's 2+2?";

    private MemoryCardGameHandler gameHandler = new MemoryCardGameHandler(new RedisTemplate<>());

    @Test
    public void addGame_withCorrectRequest_createsGameAndReturnsUUID() {
        var request = new CreateMemoryGameRequest();
        request.setGameType("MEMORY_MATCH");
        request.setTime(null);
        request.setPlayerIds(List.of(PLAYER_ID));
        request.setQuestions(List.of(buildGameQuestion()));

        var gameUUID = gameHandler.addGame(request);
        var game = gameHandler.getGames().get(gameUUID);

        Assertions.assertThat(gameUUID).isNotNull();
        Assertions.assertThat(gameHandler.getGames()).isNotNull().isNotEmpty().containsKey(gameUUID);

        Assertions.assertThat(game).isInstanceOf(MemoryCardGame.class);
        for (Object gameState : game.getGameStates()) {
            Assertions.assertThat(gameState)
                    .isInstanceOf(MemoryCardGameState.class);
        }
        Assertions.assertThat(game.getGameStates()).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(game.getGameStates().get(0)).isInstanceOf(MemoryCardGameState.class);
        Assertions.assertThat(game.getQuestions()).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(((MemoryCardGameState)game.getGameStates().get(0)).getPlayers().get(0).getCards())
                .hasSize(2);
    }

    private GameQuestion buildGameQuestion() {
        var answer = new Answer();
        answer.setValue("4");
        answer.setIsCorrect(true);

        var question = new GameQuestion();
        question.setId(QUESTION_ID);
        question.setText(QUESTION_TEXT);
        question.setAnswerType(AnswerType.NUMBER.name());
        question.setAnswers(List.of(answer));
        return question;
    }
}
