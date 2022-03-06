package com.nutikas.backendnutikas.service;

import com.nutikas.backendnutikas.dto.QuestionSetRequest;
import com.nutikas.backendnutikas.dto.QuestionSetResponse;
import com.nutikas.backendnutikas.mapper.QuestionSetMapper;
import com.nutikas.backendnutikas.repository.QuestionSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionSetService {

   private final UserService userService;
   private final QuestionSetRepository questionSetRepository;

    public QuestionSetResponse createQuestionSet(String username, QuestionSetRequest questionSet) {
        var user = userService.getUser(username);

        var questionSetDTO = QuestionSetMapper.INSTANCE.requestToDTO(questionSet);

        var questionSetModel = QuestionSetMapper.INSTANCE.dtoToModel(questionSetDTO);

        return QuestionSetMapper.INSTANCE.dtoToResponse(QuestionSetMapper.INSTANCE.modelToDTO(questionSetRepository.save(questionSetModel)));
    }

}
