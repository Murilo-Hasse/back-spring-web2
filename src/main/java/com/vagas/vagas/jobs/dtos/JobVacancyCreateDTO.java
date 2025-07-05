package com.vagas.vagas.jobs.dtos;

import com.vagas.vagas.enums.SENIORITY_LEVEL;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record JobVacancyCreateDTO(
        @NotBlank
        String title,

        @NotBlank
        @Size(min = 1, max = 1007)
        String description,

        @NotNull
        SENIORITY_LEVEL jobLevel,

        @NotNull
        String companyId, // ou opcional caso nullable

        String recruiterId

) {
}
