package com.vagas.vagas.application.dtos;

import com.vagas.vagas.candidate.dtos.CandidateSummaryDTO;
import com.vagas.vagas.enums.JOB_STATUS;
import com.vagas.vagas.jobs.dtos.JobVacancySummaryResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ApplicationResponseDTO (
        UUID id,
        JOB_STATUS status,
        LocalDateTime createdAt,
        String observation,
        CandidateSummaryDTO candidate,
        JobVacancySummaryResponseDTO jobVacancy
) {}