package com.vagas.vagas.application;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApplicationLogRepository extends MongoRepository<ApplicationLogEntity, String> {
    Optional<ApplicationLogEntity> findByApplicationId(UUID applicationId);
    List<ApplicationLogEntity> findByStatusChanges_ActorIdAndStatusChanges_UpdatedAtBetween(
            String actorId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
