package com.vagas.vagas.recruiter;

import com.vagas.vagas.recruiter.dtos.RecruiterRegisterDTO;
import com.vagas.vagas.recruiter.dtos.RecruiterResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface RecruiterMapper {
    RecruiterMapper INSTANCE = Mappers.getMapper(RecruiterMapper.class);

    // Mapeia os campos do UserEntity aninhado para o DTO de resposta

    RecruiterResponseDTO toResponseDTO(RecruiterEntity recruiter);

    @Mapping(source = "name", target = "user.name")
    @Mapping(source = "email", target = "user.email")
    @Mapping(source = "phone", target = "user.phone")
    RecruiterEntity toEntity(RecruiterRegisterDTO recruiter);

    default UUID stringToUuid(String id) {
        return id == null ? null : UUID.fromString(id);
    }

    default String uuidToString(UUID id) {
        return id == null ? null : id.toString();
    }
}
