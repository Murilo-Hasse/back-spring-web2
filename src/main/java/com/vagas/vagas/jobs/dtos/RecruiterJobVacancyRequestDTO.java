package com.vagas.vagas.jobs.dtos;

import com.vagas.vagas.enums.SENIORITY_LEVEL;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

// DTO para a requisição do Recrutador
public record RecruiterJobVacancyRequestDTO(
        @NotBlank(message = "O título é obrigatório")
        String title,

        @NotBlank(message = "A descrição é obrigatória")
        String description,

        @NotNull(message = "O nível de senioridade é obrigatório")
        SENIORITY_LEVEL jobLevel,

        // O ID da empresa é obrigatório
        @NotNull(message = "O ID da empresa é obrigatório")
        UUID companyId
) {}