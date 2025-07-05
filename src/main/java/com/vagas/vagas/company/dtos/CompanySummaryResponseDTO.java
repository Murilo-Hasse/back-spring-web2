package com.vagas.vagas.company.dtos;

import java.util.UUID;

public record CompanySummaryResponseDTO(
        UUID id,
        String name
) {
}
