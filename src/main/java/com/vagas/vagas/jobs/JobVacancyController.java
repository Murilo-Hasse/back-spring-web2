package com.vagas.vagas.jobs;

import com.vagas.vagas.jobs.dtos.CompanyJobVacancyRequestDTO;
import com.vagas.vagas.jobs.dtos.JobVacancyResponseDTO;
import com.vagas.vagas.jobs.dtos.RecruiterJobVacancyRequestDTO;
import com.vagas.vagas.user.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
public class JobVacancyController {

    private final JobVacancyService jobVacancyService;

    // ----- NOVOS ENDPOINTS DE CRIAÇÃO -----

    /**
     * Endpoint para EMPRESA criar uma vaga.
     * O ID da empresa é inferido do usuário autenticado.
     * O ID do recrutador é opcional no corpo da requisição.
     */
    @PostMapping("/by-company")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<JobVacancyResponseDTO> createByCompany(
            @Valid @RequestBody CompanyJobVacancyRequestDTO requestDTO,
            @AuthenticationPrincipal UserEntity companyUser) {

        // O serviço usará companyUser.getId() para encontrar o CompanyProfile
        JobVacancyResponseDTO response = jobVacancyService.createByCompany(requestDTO, companyUser.getIdUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint para RECRUTADOR criar uma vaga.
     * O ID do recrutador é inferido do usuário autenticado.
     * O ID da empresa é obrigatório no corpo da requisição.
     */
    @PostMapping("/by-recruiter")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<JobVacancyResponseDTO> createByRecruiter(
            @Valid @RequestBody RecruiterJobVacancyRequestDTO requestDTO,
            @AuthenticationPrincipal UserEntity recruiterUser) {

        // O serviço usará recruiterUser.getId() para encontrar o RecruiterProfile
        JobVacancyResponseDTO response = jobVacancyService.createByRecruiter(requestDTO, recruiterUser.getIdUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<JobVacancyResponseDTO>> getAll() {
        List<JobVacancyResponseDTO> vacancies = jobVacancyService.findAll();
        return ResponseEntity.ok(vacancies);
    }

    /**
     * Endpoint público para buscar uma vaga específica pelo seu ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<JobVacancyResponseDTO> getById(@PathVariable UUID id) {
        JobVacancyResponseDTO vacancy = jobVacancyService.findById(id);
        return ResponseEntity.ok(vacancy);
    }

    // ----- Endpoint de ATUALIZAÇÃO (Update) -----

    /**
     * Endpoint protegido para atualizar uma vaga existente.
     * Apenas o recrutador ou a empresa que criou a vaga pode atualizá-la.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'COMPANY')")
    public ResponseEntity<JobVacancyResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody RecruiterJobVacancyRequestDTO requestDTO,
            @AuthenticationPrincipal UserEntity user) throws AccessDeniedException {

        JobVacancyResponseDTO updatedVacancy = jobVacancyService.update(id, requestDTO, user.getIdUser());
        return ResponseEntity.ok(updatedVacancy);
    }

    // ----- Endpoint de DELEcaO (Delete) -----

    /**
     * Endpoint protegido para deletar uma vaga.
     * Apenas o recrutador ou a empresa que criou a vaga pode deleta-la.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'COMPANY')")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserEntity user) throws AccessDeniedException {

        jobVacancyService.delete(id, user.getIdUser());
        return ResponseEntity.noContent().build();
    }
}
