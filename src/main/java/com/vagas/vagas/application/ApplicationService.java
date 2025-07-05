package com.vagas.vagas.application;

import com.vagas.vagas.application.dtos.ApplicationCreateDTO;
import com.vagas.vagas.application.dtos.ApplicationResponseDTO;
import com.vagas.vagas.candidate.CandidateEntity;
import com.vagas.vagas.candidate.CandidateRepository;
import com.vagas.vagas.enums.JOB_STATUS;
import com.vagas.vagas.exceptions.ResourceNotFoundException;
import com.vagas.vagas.jobs.JobVacancyEntity;
import com.vagas.vagas.jobs.JobVacancyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final CandidateRepository candidateRepository; // Supondo que exista
    private final JobVacancyRepository jobVacancyRepository; // Supondo que exista
    private final ApplicationLogService logService;


    @Transactional
    public ApplicationEntity createApplication(UUID candidateId, UUID jobVacancyId) {
        // 1. Valida se o candidato e a vaga existem
        CandidateEntity candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidato não encontrado!"));

        JobVacancyEntity jobVacancy = jobVacancyRepository.findById(jobVacancyId)
                .orElseThrow(() -> new RuntimeException("Vaga não encontrada!"));

        // 2. Cria a nova entidade de candidatura
        ApplicationEntity newApplication = new ApplicationEntity();
        newApplication.setCandidate(candidate);
        newApplication.setJobVacancy(jobVacancy);
        newApplication.setStatus(JOB_STATUS.INSCRITO); // Define o status inicial

        // 3. Salva a nova candidatura no banco relacional
        ApplicationEntity savedApplication = applicationRepository.save(newApplication);


        logService.logStatusChange(
                savedApplication.getId(),
                savedApplication.getStatus(),
                candidate.getId().toString(),
                "CANDIDATE" // Um novo tipo de ator para a criação
        );

        return savedApplication;
    }


    @Transactional
    public void updateApplicationStatus(UUID applicationId, JOB_STATUS newStatus, String actorId, String actorType) {
        ApplicationEntity application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Candidatura não encontrada!"));

        application.setStatus(newStatus);
        applicationRepository.save(application);

        logService.logStatusChange(applicationId, newStatus, actorId, actorType);
    }
}