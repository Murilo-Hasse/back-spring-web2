package com.vagas.vagas.recruiter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RecruiterRepository extends JpaRepository<RecruiterEntity, UUID> {
    Optional<RecruiterEntity> findByUser_IdUser(UUID userId);
}
