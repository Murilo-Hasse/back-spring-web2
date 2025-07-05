package com.vagas.vagas.application;

import com.vagas.vagas.application.dtos.ApplicationCreateDTO;
import com.vagas.vagas.application.dtos.ApplicationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface ApplicationMapper{
    ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);

    @Mappings({})
    ApplicationEntity toEntity(ApplicationCreateDTO application);

    @Mappings({})
    ApplicationResponseDTO toResponseDTO(ApplicationEntity application);

    default UUID stringToUuid(String id) {
        return id == null ? null : UUID.fromString(id);
    }

    default String uuidToString(UUID id) {
        return id == null ? null : id.toString();
    }


//    ApplicationResponseDTO toResponseDTO(ApplicationEntity applicationEntity);
}
