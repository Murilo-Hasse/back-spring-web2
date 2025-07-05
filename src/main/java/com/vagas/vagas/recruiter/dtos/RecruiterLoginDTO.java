package com.vagas.vagas.recruiter.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RecruiterLoginDTO (
        @NotBlank
        @Email
        String email,

        @NotBlank
        String password
)
{
}