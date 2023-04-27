package ee.nutikas.games.api.game.service;

import ee.nutikas.games.api.game.dto.GameRequest;
import ee.nutikas.games.api.game.dto.GameResponse;
import ee.nutikas.games.api.game.mapper.GameMapper;
import ee.nutikas.games.api.game.repository.GameRepository;
import ee.nutikas.games.api.questionset.service.QuestionSetService;
import ee.nutikas.games.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameService {

    private final GameRepository repository;
    private final QuestionSetService questionSetService;
    private final UserService userService;

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

}
