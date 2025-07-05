package com.vagas.vagas.candidate;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
    List<CandidateEntity> searchCandidateEntityById(UUID id);
}
