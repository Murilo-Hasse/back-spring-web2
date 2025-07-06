package com.vagas.vagas.candidate;

import com.vagas.vagas.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
    List<CandidateEntity> searchCandidateEntityById(UUID id);
    Optional<CandidateEntity> findByUserIdUser(UUID idUser);
}
