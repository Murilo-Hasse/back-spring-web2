package com.vagas.vagas.company.dtos;

public record CompanyResponseDTO (
        String id,
        String name,
        String email,
        String password,
        String phone,
        String SocialNumber,
        String address
)
{}
