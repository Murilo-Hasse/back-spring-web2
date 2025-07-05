package com.vagas.vagas.recruiter.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RecruiterRegisterDTO (
        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 4, max = 50)
        String password,

        @NotBlank
        String phone
) {
}
