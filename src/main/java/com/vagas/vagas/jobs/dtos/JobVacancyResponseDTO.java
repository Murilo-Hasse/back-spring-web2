package com.vagas.vagas.jobs.dtos;

import com.vagas.vagas.company.dtos.CompanySummaryResponseDTO;
import com.vagas.vagas.enums.SENIORITY_LEVEL;
import com.vagas.vagas.recruiter.dtos.RecruiterSummaryResponseDTO;

public record JobVacancyResponseDTO (
    String id,
    String title,
    String description,
    SENIORITY_LEVEL jobLevel,

    CompanySummaryResponseDTO company,
    RecruiterSummaryResponseDTO recruiter
) {
}

