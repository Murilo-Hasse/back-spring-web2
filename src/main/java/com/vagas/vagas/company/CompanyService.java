package com.vagas.vagas.company;

import com.vagas.vagas.company.dtos.CompanyRegisterDTO;
import com.vagas.vagas.company.dtos.CompanyResponseDTO;
import com.vagas.vagas.company.dtos.CompanyUpdateDTO;
import com.vagas.vagas.exceptions.ResourceNotFoundException;
import com.vagas.vagas.user.USER_ROLE;
import com.vagas.vagas.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final PasswordEncoder passwordEncoder;

    public CompanyResponseDTO registerCompany(CompanyRegisterDTO dto) {

        UserEntity newUser = new UserEntity();
        newUser.setName(dto.name());
        newUser.setEmail(dto.email());
        newUser.setPhone(dto.phone());
        newUser.setPassword(passwordEncoder.encode(dto.password()));
        newUser.setRole(USER_ROLE.COMPANY);

        CompanyEntity companyProfile = new CompanyEntity();
        companyProfile.setAddress(dto.address());
        companyProfile.setSocialNumber(dto.socialNumber());

        companyProfile.setUser(newUser);

        companyRepository.save(companyProfile);

        return companyMapper.toResponseDTO(companyProfile);
    }

    public CompanyResponseDTO findById(UUID id) {
        return companyRepository.findById(id)
                .map(companyMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Compania de id " + id + " não existe"));
    }

    // O metodo de login para Company seria implementado aqui, similar ao Candidate
    // public CompanyResponseDTO loginCompany(CompanyLoginDTO companyLoginDTO) {
    //     try {
    //         companyRepository.searchCompanyEntityById(companyLoginDTO.email());
    //     }
    //     // Lógica de autenticação e retorno do DTO
    // }


    public Set<CompanyResponseDTO> findAll() {
        List<CompanyEntity> companies = companyRepository.findAll();
        return companies.stream().map(companyMapper::toResponseDTO).collect(Collectors.toSet());
    }

    public CompanyResponseDTO updateCompany(UUID companyId, CompanyUpdateDTO updateDTO) {
        CompanyEntity entity = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com o ID: " + companyId));

        updateDTO.name().ifPresent(entity::setName);
        updateDTO.phone().ifPresent(entity::setPhone);
        updateDTO.name().ifPresent(entity::setName); // Exemplo de campo específico para Company
        updateDTO.socialNumber().ifPresent(entity::setSocialNumber); // Exemplo de campo específico para Company
        updateDTO.address().ifPresent(entity::setAddress);

        CompanyEntity updatedEntity = companyRepository.save(entity);

        return companyMapper.toResponseDTO(updatedEntity);
    }

    public void deleteById(UUID id) {
        companyRepository.deleteById(id);
    }
}
