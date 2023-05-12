package ee.nutikas.games.engine.memorycards;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.nutikas.games.engine.*;
import ee.nutikas.games.engine.memorycards.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoryCardGameHandler implements GameHandler<MemoryCardGame, CreateGameRequest> {

    private static final String REDIS_KEY_PREFIX = "memoryCardGame:";
    private final RedisTemplate<String, MemoryCardGame> redisTemplate;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper objectMapper;

    @PostConstruct
    private void initRedis() {
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
    }

    @Override
    public UUID addGame(CreateGameRequest request) {
        /* TODO
        * If game is ongoing, return that UUID instead.
        * If player already is in ongoing game with the same Game ID
        * then they can't add a new game before ending the last one.
        * */
        if (!GameType.MEMORY_MATCH.equals(GameType.fromString(request.getGameType()))) {
            throw new IllegalArgumentException("INVALID_GAME_TYPE");
        }

        // Generate a unique ID for the game
        var uuid = UUID.randomUUID();
        var game = new MemoryCardGame();

        game.setQuestions(request.getQuestions());
        // Set up the initial Game State based on the request
        var state = new MemoryCardGameState();

        // SET UP PLAYERS
        var players = request.getPlayerIds().stream().map(id -> {
            var player = new MemoryGamePlayer();
            player.setId(id);
            player.setScore(0);
            return player;
        }).toList();

        // SET UP CARDS
        var cards = new ArrayList<MemoryCard>();
        for (int i = 0; i < request.getQuestions().size(); i++) {
            var questionId = request.getQuestions().get(i).getId();
            var questionCard = new MemoryCard();
            questionCard.setId(i * 2L);
            questionCard.setQuestionId(questionId);
            questionCard.setText(request.getQuestions().get(i).getText());

            var answerCard = new MemoryCard();
            answerCard.setId(i * 2L + 1);
            answerCard.setQuestionId(questionId);
            answerCard.setText(request.getQuestions().get(i).getAnswers().stream().filter(Answer::getIsCorrect).findFirst().orElseThrow().getValue());
            cards.add(questionCard);
            cards.add(answerCard);
        }
        players.forEach(p -> p.setCards(cards));

        state.setPlayers(players);
        state.setTime(request.getTime());
        var states = new ArrayList<MemoryCardGameState>();
        states.add(state);
        game.setGameStates(states);

        String key = REDIS_KEY_PREFIX + uuid;
        redisTemplate.opsForValue().set(key, game);
        redisTemplate.expire(key, 1, TimeUnit.DAYS);

        // Return the Game ID
        return uuid;
    }

    @Override
    public Map<UUID, MemoryCardGame> getGames() {
        Set<String> keys = redisTemplate.keys(REDIS_KEY_PREFIX + "*");
        if (keys == null) {
            return Collections.emptyMap();
        }

        return keys.stream()
                .map(key -> new AbstractMap.SimpleEntry<>(
                    UUID.fromString(key.replace(REDIS_KEY_PREFIX, "")),
                    Optional.ofNullable(redisTemplate.opsForValue().get(key))
                ))
                .filter(entry -> entry.getValue().isPresent())
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue().get()
                ));
    }

    @Override
    public void publishLatestState(UUID uuid) {
        var key = REDIS_KEY_PREFIX + uuid.toString();
        var game = redisTemplate.opsForValue().get(key);

        if (game == null) {
            throw new IllegalArgumentException("Game with UUID: " + uuid + " not found");
        }

        var latestState = game.getGameStates().get(game.getGameStates().size() - 1);

        // Shuffle the cards for each player
        latestState.getPlayers().forEach(p -> Collections.shuffle(p.getCards()));

        simpMessagingTemplate.convertAndSend("/topic/game/" + uuid, latestState);

    }

    public void handleGameStateUpdate(UUID uuid, GameUpdate gameUpdate) {
        var key = REDIS_KEY_PREFIX + uuid.toString();
        var game = redisTemplate.opsForValue().get(key);

        if (game == null) {
            throw new IllegalArgumentException("Game with UUID: " + uuid + " not found");
        }

        var latestState = game.getGameStates().get(game.getGameStates().size() - 1);
        if (latestState.getPlayers().get(0).getLastClickedCardIds() == null ||
            latestState.getPlayers().get(0).getLastClickedCardIds().size() % 2 == 0) {
            var newState = new MemoryCardGameState();
            newState.setTime(latestState.getTime());
            newState.setPlayers(List.copyOf(latestState.getPlayers()));
            newState.getPlayers().get(0).setLastClickedCardIds(List.of(gameUpdate.clickedCard()));
            game.getGameStates().add(newState);
        } else {
            var lastCard = latestState.getPlayers().get(0).getLastClickedCardIds().get(latestState.getPlayers().get(0).getLastClickedCardIds().size() - 1);
            var penultimateCard = latestState.getPlayers().get(0).getLastClickedCardIds().get(latestState.getPlayers().get(0).getLastClickedCardIds().size() - 2);

        }

        redisTemplate.opsForValue().set(key, game);
        redisTemplate.expire(key, 1, TimeUnit.DAYS);

    }
}
