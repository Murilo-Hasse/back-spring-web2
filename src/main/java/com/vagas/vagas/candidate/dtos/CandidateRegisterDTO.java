package com.vagas.vagas.candidate.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record CandidateRegisterDTO(
        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 4, max = 50)
        String password,

        @NotBlank
        String phone,



        String socialNumber,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthdate
) {
}
