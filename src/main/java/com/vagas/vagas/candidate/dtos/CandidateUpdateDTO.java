package com.vagas.vagas.candidate.dtos;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Optional;

public record CandidateUpdateDTO(
        Optional<String> name,
        Optional<String> phone,
        Optional<String> socialNumber,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        Optional<LocalDate> birthDate
) {}
