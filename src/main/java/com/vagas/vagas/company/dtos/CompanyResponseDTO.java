package com.vagas.vagas.company.dtos;

import java.util.UUID;

public record CompanyResponseDTO(
        UUID id,
        UUID userId,
        String name,
        String email,
        String phone,
        String socialNumber, // <-- Corrigido para camelCase (s minúsculo)
        String address
) {}
