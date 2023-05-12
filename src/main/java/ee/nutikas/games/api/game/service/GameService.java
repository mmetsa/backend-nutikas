package ee.nutikas.games.api.game.service;

import ee.nutikas.games.api.game.dto.GameRequest;
import ee.nutikas.games.api.game.dto.GameResponse;
import ee.nutikas.games.api.game.mapper.GameMapper;
import ee.nutikas.games.api.game.repository.GameRepository;
import ee.nutikas.games.api.questionset.service.QuestionSetService;
import ee.nutikas.games.api.security.service.UserDetailsImpl;
import ee.nutikas.games.api.user.service.UserService;
import ee.nutikas.games.engine.GameEngine;
import ee.nutikas.games.engine.memorycards.MemoryCardGameHandler;
import ee.nutikas.games.engine.memorycards.dto.Answer;
import ee.nutikas.games.engine.memorycards.dto.CreateMemoryGameRequest;
import ee.nutikas.games.engine.memorycards.dto.GameQuestion;
import ee.nutikas.games.engine.memorycards.dto.GameType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameService {

    private final GameRepository repository;
    private final QuestionSetService questionSetService;
    private final UserService userService;
    private final GameEngine gameEngine;

    public void createGame(GameRequest gameRequest, Long userId) {
        var model = GameMapper.INSTANCE.toModel(gameRequest);
        var questionSet = questionSetService.findById(gameRequest.getQuestionSetId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Question Set ID"));
        model.setQuestionSet(questionSet);
        model.setCreator(userService.findUserById(userId).orElseThrow());

        model.setCode(GameCodeGenerator.generateCode());
        repository.save(model);
    }

    public List<GameResponse> getTeacherGames(Long userId) {
        var games = repository.findByCreatorId(userId);
        return GameMapper.INSTANCE.toResponseList(games);
    }

    public List<GameResponse> getStudentGames(Long userId) {
        var user = userService.findUserById(userId).orElseThrow();
        List<Long> classIds = user.getUserRoles().stream()
                .map(role -> role.getClassModel().getId())
                .toList();
        var games = repository.findByClassIdIsIn(classIds);
        return GameMapper.INSTANCE.toResponseList(games);
    }

    public UUID joinGameById(UserDetailsImpl user, Long id) {
        // TODO: Add user validation
        var game = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Game ID"));

        // TODO: This should be generic somehow, not MEMORY_MATCH specific
        var request = new CreateMemoryGameRequest();
        request.setGameType(GameType.MEMORY_MATCH.name());
        request.setTime(game.getGameTime());
        request.setPlayerIds(List.of(user.getId()));

        var questions = new ArrayList<GameQuestion>();
        game.getQuestionSet().getQuestions().forEach(q -> {
            var question = new GameQuestion();
            question.setId(q.getId());
            question.setText(q.getText());
            question.setPointValue(game.getPointsPerQuestion());
            question.setAnswerType(q.getAnswerType().name());
            question.setAnswers(new ArrayList<>());
            q.getAnswers().forEach(a -> {
                var answer = new Answer();
                answer.setValue(a.getValue());
                answer.setIsCorrect(a.getIsCorrect());
                question.getAnswers().add(answer);
            });
            questions.add(question);
        });
        request.setQuestions(questions);

        return gameEngine.joinGame(request);
    }

}
