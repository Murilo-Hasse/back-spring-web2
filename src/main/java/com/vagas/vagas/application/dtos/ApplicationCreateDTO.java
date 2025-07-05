package com.vagas.vagas.application.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ApplicationCreateDTO (
        @NotNull(message = "O ID da vaga não pode ser nulo")
        UUID jobVacancyId
){

}
