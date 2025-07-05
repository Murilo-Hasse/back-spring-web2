package com.vagas.vagas.application;

import com.vagas.vagas.enums.JOB_STATUS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationLogService {
    private final ApplicationLogRepository logRepository;

    public void logStatusChange(UUID applicationId, JOB_STATUS newStatus, String actorId, String actorType) {
        ApplicationLogEntity logEntity = logRepository.findByApplicationId(applicationId)
                .orElseGet(() -> {
                    ApplicationLogEntity newLog = new ApplicationLogEntity();
                    newLog.setApplicationId(applicationId);
                    return newLog;
                });

        ApplicationLogEntity.StatusChange change = ApplicationLogEntity.StatusChange.builder()
                .newStatus(newStatus)
                .updatedAt(LocalDateTime.now())
                .actorId(actorId)
                .actorType(actorType)
                .build();

        logEntity.getStatusChanges().add(change);

        logRepository.save(logEntity);
    }

}
