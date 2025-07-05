package com.vagas.vagas.jobs;

import com.vagas.vagas.company.CompanyEntity;
import com.vagas.vagas.company.CompanyRepository;
import com.vagas.vagas.enums.SENIORITY_LEVEL;
import com.vagas.vagas.jobs.dtos.CompanyJobVacancyRequestDTO;
import com.vagas.vagas.jobs.dtos.JobVacancyResponseDTO;
import com.vagas.vagas.jobs.dtos.RecruiterJobVacancyRequestDTO;
import com.vagas.vagas.recruiter.RecruiterEntity;
import com.vagas.vagas.recruiter.RecruiterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobVacancyService {

    // Injeção de todas as dependências necessárias
    private final JobVacancyRepository jobVacancyRepository;
    private final CompanyRepository companyRepository;
    private final RecruiterRepository recruiterRepository;
    private final JobVacancyMapper jobVacancyMapper;

    // --- MÉTODOS DE CRIAÇÃO ---

    @Transactional
    public JobVacancyResponseDTO createByCompany(CompanyJobVacancyRequestDTO dto, UUID companyUserId) {
        // 1. Encontra o perfil da empresa que está criando a vaga
        CompanyEntity company = companyRepository.findByUser_IdUser(companyUserId)
                .orElseThrow(() -> new RuntimeException("Perfil da empresa não encontrado para o usuário logado."));

        // 2. Encontra o recrutador associado (se um 'ID' for fornecido)
        RecruiterEntity recruiter = null;
        if (dto.recruiterId() != null) {
            recruiter = recruiterRepository.findById(dto.recruiterId())
                    .orElseThrow(() -> new RuntimeException("Recrutador com o ID fornecido não encontrado."));
        }
        JobVacancyEntity newVacancy = new JobVacancyEntity();
        newVacancy.setTitle(dto.title());
        newVacancy.setDescription(dto.description());
        newVacancy.setJobLevel(dto.jobLevel());
        newVacancy.setCompany(company);
        newVacancy.setRecruiter(recruiter);

        JobVacancyEntity savedVacancy = jobVacancyRepository.save(newVacancy);

        return jobVacancyMapper.toResponseDTO(savedVacancy);
    }

    @Transactional
    public JobVacancyResponseDTO createByRecruiter(RecruiterJobVacancyRequestDTO dto, UUID recruiterUserId) {
        // 1. Encontra o perfil do recrutador que está criando a vaga
        RecruiterEntity recruiter = recruiterRepository.findByUser_IdUser(recruiterUserId)
                .orElseThrow(() -> new RuntimeException("Perfil do recrutador não encontrado para o usuário logado."));

        // 2. Encontra a empresa para a qual a vaga esta sendo criada (obrigatório)
        CompanyEntity company = companyRepository.findById(dto.companyId())
                .orElseThrow(() -> new RuntimeException("Empresa com o ID fornecido não encontrada."));

        // TODO: Adicionar lógica para verificar se o recrutador tem permissão para postar para esta empresa

        // 3. Cria e popula a nova entidade de vaga
        return getJobVacancyResponseDTO(recruiter, company, dto.title(), dto.description(), dto.jobLevel(), dto);
    }

    private JobVacancyResponseDTO getJobVacancyResponseDTO(RecruiterEntity recruiter, CompanyEntity company, String title, String description, SENIORITY_LEVEL seniorityLevel, RecruiterJobVacancyRequestDTO dto) {
        JobVacancyEntity newVacancy = new JobVacancyEntity();
        newVacancy.setTitle(title);
        newVacancy.setDescription(description);
        newVacancy.setJobLevel(seniorityLevel);
        newVacancy.setCompany(company);
        newVacancy.setRecruiter(recruiter);

        JobVacancyEntity savedVacancy = jobVacancyRepository.save(newVacancy);

        return jobVacancyMapper.toResponseDTO(savedVacancy);
    }

    // --- MÉTODOS CRUD (LEITURA, ATUALIZAÇÃO, DELEÇÃO) ---

    @Transactional(readOnly = true) // readOnly = true otimiza consultas de leitura
    public List<JobVacancyResponseDTO> findAll() {
        return jobVacancyRepository.findAll()
                .stream()
                .map(jobVacancyMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public JobVacancyResponseDTO findById(UUID id) {
        JobVacancyEntity vacancy = jobVacancyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaga com ID " + id + " não encontrada."));
        return jobVacancyMapper.toResponseDTO(vacancy);
    }

    // O DTO de update pode ser o mesmo do Recruiter (RecruiterJobVacancyRequestDTO)
    @Transactional
    public JobVacancyResponseDTO update(UUID vacancyId, RecruiterJobVacancyRequestDTO dto, UUID actorUserId) throws AccessDeniedException {
        JobVacancyEntity vacancy = jobVacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new RuntimeException("Vaga com ID " + vacancyId + " não encontrada."));

        // Lógica de autorização: verifica se o usuario é o recrutador da vaga ou o dono da empresa
        validatePermission(vacancy, actorUserId);

        CompanyEntity company = companyRepository.findById(dto.companyId())
                .orElseThrow(() -> new RuntimeException("Empresa com o ID fornecido não encontrada."));

        // Atualiza os campos
        vacancy.setTitle(dto.title());
        vacancy.setDescription(dto.description());
        vacancy.setJobLevel(dto.jobLevel());
        vacancy.setCompany(company);

        JobVacancyEntity updatedVacancy = jobVacancyRepository.save(vacancy);
        return jobVacancyMapper.toResponseDTO(updatedVacancy);
    }

    @Transactional
    public void delete(UUID vacancyId, UUID actorUserId) throws AccessDeniedException {
        JobVacancyEntity vacancy = jobVacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new RuntimeException("Vaga com ID " + vacancyId + " não encontrada."));

        // A mesma verificação de permissão antes de deletar
        validatePermission(vacancy, actorUserId);

        jobVacancyRepository.delete(vacancy);
    }

    // Método auxiliar privado para validar permissões
    private void validatePermission(JobVacancyEntity vacancy, UUID actorUserId) throws AccessDeniedException {
        boolean isOwnerCompany = vacancy.getCompany().getUser().getIdUser().equals(actorUserId);
        boolean isOwnerRecruiter = vacancy.getRecruiter() != null && vacancy.getRecruiter().getUser().getIdUser().equals(actorUserId);

        if (!isOwnerCompany && !isOwnerRecruiter) {
            throw new AccessDeniedException("Usuário não tem permissão para modificar esta vaga.");
        }
    }
}
