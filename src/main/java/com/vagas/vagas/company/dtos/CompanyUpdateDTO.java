package com.vagas.vagas.company.dtos;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Optional;

public record CompanyUpdateDTO(
        Optional<String> name,
        Optional<String> phone,
        Optional<String> socialNumber,
        Optional<String> address
) {
}
