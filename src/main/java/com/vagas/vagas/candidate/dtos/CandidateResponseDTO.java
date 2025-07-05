package com.vagas.vagas.candidate.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record CandidateResponseDTO(
        String id,
        String name,
        String email,
        String password,
        String phone,
        String SocialNumber
)
{}
