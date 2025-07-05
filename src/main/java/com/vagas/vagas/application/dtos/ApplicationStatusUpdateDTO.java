package com.vagas.vagas.application.dtos;

import com.vagas.vagas.enums.JOB_STATUS;
import jakarta.validation.constraints.NotNull;

public record ApplicationStatusUpdateDTO(
        @NotNull(message = "O novo status não pode ser nulo")
        JOB_STATUS newStatus
) {}