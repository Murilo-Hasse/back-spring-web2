package com.vagas.vagas.application;

import com.vagas.vagas.candidate.CandidateEntity;
import com.vagas.vagas.enums.JOB_STATUS;
import com.vagas.vagas.jobs.JobVacancyEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false) // updatable = false para garantir que não seja alterado
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Boa prática para armazenar o nome do enum no DB
    private JOB_STATUS status;

    @Column()
    private String observation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_id", nullable = false)
    private CandidateEntity candidate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "job_id",  nullable = false)
    private JobVacancyEntity jobVacancy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
