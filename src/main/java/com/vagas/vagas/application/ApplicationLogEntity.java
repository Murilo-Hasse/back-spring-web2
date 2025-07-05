package com.vagas.vagas.application;

import com.vagas.vagas.enums.JOB_STATUS;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collation = "")
public class ApplicationLogEntity {

    @Id
    private String id;

    private UUID applicationId;

    private List<StatusChange> statusChanges;

    public ApplicationLogEntity() {
        this.statusChanges = new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StatusChange {
        private LocalDateTime updatedAt;
        private JOB_STATUS newStatus;
        private String actorId; // Renomeado para "ator" (quem fez a ação)
        private String actorType;
    }
}
