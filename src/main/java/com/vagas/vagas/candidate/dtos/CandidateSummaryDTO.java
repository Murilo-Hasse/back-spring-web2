package com.vagas.vagas.candidate.dtos;

import java.util.UUID;

public record CandidateSummaryDTO(
    UUID id,
    String name,
    String email
) {}
