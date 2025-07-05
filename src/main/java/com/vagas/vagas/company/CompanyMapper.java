package com.vagas.vagas.company;

import com.vagas.vagas.company.dtos.CompanyRegisterDTO;
import com.vagas.vagas.company.dtos.CompanyResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface CompanyMapper {
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    // Mapeia os campos do UserEntity aninhado para o DTO de resposta
    CompanyResponseDTO toResponseDTO(CompanyEntity company);

    @Mapping(source = "name", target = "user.name")
    @Mapping(source = "email", target = "user.email")
    @Mapping(source = "phone", target = "user.phone")
    CompanyEntity toEntity(CompanyRegisterDTO company);
    default UUID stringToUuid(String id) {
        return id == null ? null : UUID.fromString(id);
    }

    default String uuidToString(UUID id) {
        return id == null ? null : id.toString();
    }
}
