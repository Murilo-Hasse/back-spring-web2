package com.vagas.vagas.candidate.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CandidateLoginDTO(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String password
) {
}
