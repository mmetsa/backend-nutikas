package ee.nutikas.games.api.questionset.service;

import ee.nutikas.games.api.questionset.dto.QuestionSetDTO;
import ee.nutikas.games.api.questionset.dto.QuestionSetRequest;
import ee.nutikas.games.api.questionset.mapper.QuestionSetMapper;
import ee.nutikas.games.api.questionset.model.QuestionSetModel;
import ee.nutikas.games.api.questionset.repository.QuestionSetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionSetService {

    private final QuestionSetRepository repository;

    public void save(QuestionSetRequest questionSet, Long userId) {
        var model = QuestionSetMapper.INSTANCE.toModel(questionSet);
        model.getQuestions().forEach(question ->  {
            question.setQuestionSet(model);
            question.getAnswers().forEach(answer -> answer.setQuestion(question));
        });
        model.setOwnerId(userId);
        repository.save(model);
    }

    public List<QuestionSetDTO> getQuestionSets(Long userId) {
        return repository.findAllByOwnerId(userId).stream()
                .map(QuestionSetMapper.INSTANCE::toDTO)
                .toList();
    }

    public Optional<QuestionSetModel> findById(Long questionSetId) {
        return repository.findById(questionSetId);
    }

}
