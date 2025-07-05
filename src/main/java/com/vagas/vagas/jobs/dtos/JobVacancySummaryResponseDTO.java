package  com.vagas.vagas.jobs.dtos;


import com.vagas.vagas.company.dtos.CompanySummaryResponseDTO;

import java.util.UUID;

public record JobVacancySummaryResponseDTO(
        UUID id,
        String title,
        CompanySummaryResponseDTO company
)
{}