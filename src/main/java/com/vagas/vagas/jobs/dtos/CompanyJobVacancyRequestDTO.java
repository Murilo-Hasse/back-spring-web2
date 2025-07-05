package com.vagas.vagas.jobs.dtos;

import com.vagas.vagas.enums.SENIORITY_LEVEL;
import jakarta.validation.constraints.NotBlank;// DTO para a requisição da Empresa
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CompanyJobVacancyRequestDTO(
        @NotBlank (message = "O título é obrigatório")
        String title,

        @NotBlank(message = "A descrição é obrigatória")
        String description,

        @NotNull(message = "O nível de senioridade é obrigatório")
        SENIORITY_LEVEL jobLevel,

        // O ID do recrutador é opcional. Pode ser nulo.
        UUID recruiterId
) {}