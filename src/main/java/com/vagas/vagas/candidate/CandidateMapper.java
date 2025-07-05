package com.vagas.vagas.candidate;


import com.vagas.vagas.candidate.dtos.CandidateRegisterDTO;
import com.vagas.vagas.candidate.dtos.CandidateResponseDTO;
import com.vagas.vagas.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.UUID;
@Mapper(componentModel = "spring") // 'componentModel = "spring"' permite a injeção do mapper
public interface CandidateMapper {

    CandidateMapper INSTANCE = Mappers.getMapper(CandidateMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "id", target = "id") // Mapeia o ID do próprio perfil do candidato
    @Mapping(source = "socialNumber", target = "socialNumber")
    CandidateResponseDTO toResponseDTO(CandidateEntity candidate);

    // 2. Definindo e nomeando o metodo para ser encontrado pelo MapStruct
    @Named("mapUserIdFromUser")
    default UUID mapUserIdFromUser(UserEntity user) {
        // 3. Implementando a lógica customizada
        if (user == null) {
            return null;
        }
        return user.getIdUser();
    }

    default UUID stringToUuid(String id) {
        return id == null ? null : UUID.fromString(id);
    }

    default String uuidToString(UUID id) {
        return id == null ? null : id.toString();
    }
}
