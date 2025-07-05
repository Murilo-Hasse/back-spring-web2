package com.vagas.vagas.recruiter;

import com.vagas.vagas.exceptions.ResourceNotFoundException;
import com.vagas.vagas.recruiter.dtos.RecruiterRegisterDTO;
import com.vagas.vagas.recruiter.dtos.RecruiterResponseDTO;
import com.vagas.vagas.recruiter.dtos.RecruiterUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruiterService {

    private final RecruiterRepository recruiterRepository;
    private final RecruiterMapper recruiterMapper;
    private final PasswordEncoder passwordEncoder;

    public RecruiterResponseDTO registerRecruiter(RecruiterRegisterDTO recruiterRegisterDTO) {
        RecruiterEntity recruiterEntity = recruiterMapper.toEntity(recruiterRegisterDTO);
        recruiterEntity.setPassword(passwordEncoder.encode(recruiterRegisterDTO.password()));
        RecruiterEntity savedRecruiter = recruiterRepository.save(recruiterEntity);

        return recruiterMapper.toResponseDTO(savedRecruiter);
    }

    // O metodo de login para recruiter seria implementado aqui, similar ao Candidate
    // public recruiterResponseDTO loginrecruiter(recruiterLoginDTO recruiterLoginDTO) {
    //     try {
    //         recruiterRepository.searchrecruiterEntityById(recruiterLoginDTO.email());
    //     }
    //     // Lógica de autenticação e retorno do DTO
    // }

    public RecruiterResponseDTO findById(UUID id) {
        return recruiterRepository.findById(id)
                .map(recruiterMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Recrutador de id " + id + " não existe"));
    }

    public Set<RecruiterResponseDTO> findAll() {
        List<RecruiterEntity> recruiters = recruiterRepository.findAll();
        return recruiters.stream().map(recruiterMapper::toResponseDTO).collect(Collectors.toSet());
    }

   public RecruiterResponseDTO updaterecruiter(UUID recruiterId, RecruiterUpdateDTO updateDTO) {
       RecruiterEntity entity = recruiterRepository.findById(recruiterId)
               .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com o ID: " + recruiterId));

       updateDTO.name().ifPresent(entity::setName);
       updateDTO.phone().ifPresent(entity::setPhone);

       RecruiterEntity updatedEntity = recruiterRepository.save(entity);

       return recruiterMapper.toResponseDTO(updatedEntity);
   }

    public void deleteById(UUID id) {
        recruiterRepository.deleteById(id);
    }
}
