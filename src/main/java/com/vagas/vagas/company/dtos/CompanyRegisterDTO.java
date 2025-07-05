package com.vagas.vagas.company.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CompanyRegisterDTO (
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

        @NotBlank
        String socialNumber,

        @NotBlank
        String address
){
}
