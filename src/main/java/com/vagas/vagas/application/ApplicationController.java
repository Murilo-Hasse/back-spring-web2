package com.vagas.vagas.application;

import com.vagas.vagas.application.dtos.ApplicationCreateDTO;
import com.vagas.vagas.application.dtos.ApplicationResponseDTO;
import com.vagas.vagas.application.dtos.ApplicationStatusUpdateDTO;
import com.vagas.vagas.candidate.CandidateEntity;
import com.vagas.vagas.candidate.CandidateRepository;
import com.vagas.vagas.candidate.dtos.CandidateSummaryDTO;
import com.vagas.vagas.company.CompanyEntity;
import com.vagas.vagas.company.dtos.CompanySummaryResponseDTO;
import com.vagas.vagas.jobs.JobVacancyEntity;
import com.vagas.vagas.jobs.dtos.JobVacancySummaryResponseDTO;
import com.vagas.vagas.recruiter.RecruiterEntity;
import com.vagas.vagas.user.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;


@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final CandidateRepository candidateRepository;
    private final ApplicationMapper applicationMapper;

    @PostMapping
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<ApplicationResponseDTO> applyToJob(
            @RequestBody ApplicationCreateDTO requestDTO,
            @AuthenticationPrincipal UserEntity authenticatedUser
    ) {
        // 1. Busca o perfil de CANDIDATO (isto permanece igual e funciona)
        CandidateEntity candidateProfile = candidateRepository.findByUserIdUser(authenticatedUser.getIdUser())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Perfil de candidato não encontrado para o utilizador autenticado."
                ));

        // 2. Passa o objeto 'candidateProfile' completo para o serviço
        ApplicationEntity createdApplication = applicationService.createApplication(
                candidateProfile, // Passa o objeto inteiro
                requestDTO.jobVacancyId()
        );

        // 3. Mapeia a resposta
        ApplicationResponseDTO response = applicationMapper.toResponseDTO(createdApplication);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * Endpoint para uma empresa ou recrutador alterar o status de uma aplicação.
     * Apenas 'COMPANY' ou 'RECRUITER' devem ter acesso.
     */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('COMPANY', 'RECRUITER')") // Exemplo de como proteger com Spring Security
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody ApplicationStatusUpdateDTO requestDTO,
            @AuthenticationPrincipal UserEntity userDetails // Pega o ator da ação
    ) {
        String actorId = userDetails.getIdUser().toString();
        String actorType = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", ""); // Ex: 'COMPANY'

        applicationService.updateApplicationStatus(
                id,
                requestDTO.newStatus(),
                actorId,
                actorType
        );

        return ResponseEntity.noContent().build(); // Retorna 204 No Content, sucesso sem corpo
    }

    // --- Métodos Auxiliares ---

    /**
     * Converte uma ApplicationEntity para o DTO de resposta.
     * Idealmente, isso ficaria em uma classe Mapper dedicada (ex: ApplicationMapper).
     */
    private ApplicationResponseDTO toResponseDTO(ApplicationEntity entity) {
        CandidateEntity candidate = entity.getCandidate();
        JobVacancyEntity jobVacancy = entity.getJobVacancy();
        CompanyEntity company = jobVacancy.getCompany(); // Supondo que JobVacancyEntity tem a empresa

        CandidateSummaryDTO candidateInfo = new CandidateSummaryDTO(
                candidate.getId(),
                candidate.getName(),
                candidate.getEmail()
        );

        CompanySummaryResponseDTO companyInfo = new CompanySummaryResponseDTO(
                company.getId(),
                company.getName()
        );

        JobVacancySummaryResponseDTO jobVacancyInfo = new JobVacancySummaryResponseDTO(
                jobVacancy.getId(),
                jobVacancy.getTitle(),
                companyInfo
        );

        return new ApplicationResponseDTO(
                entity.getId(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getObservation(),
                candidateInfo,
                jobVacancyInfo
        );
    }
}