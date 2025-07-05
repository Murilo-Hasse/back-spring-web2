package com.vagas.vagas.recruiter.dtos;

import java.util.Optional;

public record RecruiterUpdateDTO(

        Optional<String> name,
        Optional<String> phone,
        Optional<String> socialNumber,
        Optional<String> address
) {
}
