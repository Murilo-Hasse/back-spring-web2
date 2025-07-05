package com.vagas.vagas.candidate;

import com.vagas.vagas.candidate.dtos.CandidateRegisterDTO;
import com.vagas.vagas.candidate.dtos.CandidateResponseDTO;
import com.vagas.vagas.candidate.dtos.CandidateUpdateDTO;
import com.vagas.vagas.exceptions.ResourceNotFoundException;
import com.vagas.vagas.user.USER_ROLE;
import com.vagas.vagas.user.UserEntity;
import com.vagas.vagas.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public CandidateResponseDTO registerCandidate(CandidateRegisterDTO dto) {
        userRepository.findByEmail(dto.email()).ifPresent(user -> {
            throw new RuntimeException("Email já cadastrado!");
        });

        // 1. CRIE O OBJETO UserEntity PRIMEIRO
        UserEntity newUser = new UserEntity();
        newUser.setName(dto.name());
        newUser.setEmail(dto.email());
        newUser.setPhone(dto.phone());
        newUser.setRole(USER_ROLE.CANDIDATE);

        // 2. DEFINA A SENHA CRIPTOGRAFADA DIRETAMENTE NO 'newUser'
        newUser.setPassword(passwordEncoder.encode(dto.password()));

        // 3. AGORA CRIE O PERFIL
        CandidateEntity candidateProfile = new CandidateEntity();
        candidateProfile.setBirthdate(dto.birthdate());
        candidateProfile.setSocialNumber(dto.socialNumber());

        // 4. SÓ AGORA FAÇA A LIGAÇÃO ENTRE ELES
        candidateProfile.setUser(newUser);

        // 5. SALVE O PERFIL (que salvará o usuário em cascata)
        CandidateEntity savedProfile = candidateRepository.save(candidateProfile);

        return candidateMapper.toResponseDTO(savedProfile);
    }


    public CandidateResponseDTO findById(UUID id) {
        return candidateRepository.findById(id)
                .map(candidateMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Candidato de id " + id + " não existe"));
    }

    public Set<CandidateResponseDTO> findAll() {
        List<CandidateEntity> candidates = candidateRepository.findAll();
        return candidates.stream().map(candidateMapper::toResponseDTO).collect(Collectors.toSet());
    }

    public CandidateResponseDTO updateCandidate(UUID candidateId, CandidateUpdateDTO updateDTO) {
        CandidateEntity entity = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidato não encontrado com o ID: " + candidateId));

        updateDTO.name().ifPresent(entity::setName);
        updateDTO.phone().ifPresent(entity::setPhone);
        updateDTO.socialNumber().ifPresent(entity::setSocialNumber);
        updateDTO.birthDate().ifPresent(entity::setBirthdate);

        CandidateEntity updatedEntity = candidateRepository.save(entity);

        return candidateMapper.toResponseDTO(updatedEntity);
    }

    public void deleteById(UUID id) {
        candidateRepository.deleteById(id);
    }
}



