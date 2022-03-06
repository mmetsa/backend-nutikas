package com.nutikas.backendnutikas.mapper;

import com.nutikas.backendnutikas.dto.QuestionSetDTO;
import com.nutikas.backendnutikas.dto.QuestionSetRequest;
import com.nutikas.backendnutikas.dto.QuestionSetResponse;
import com.nutikas.backendnutikas.model.QuestionSetModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionSetMapper {

    QuestionSetMapper INSTANCE = Mappers.getMapper(QuestionSetMapper.class);

    QuestionSetDTO requestToDTO(QuestionSetRequest request);

    QuestionSetDTO modelToDTO(QuestionSetModel model);

    QuestionSetModel dtoToModel(QuestionSetDTO dto);

    QuestionSetResponse dtoToResponse(QuestionSetDTO dto);
}
