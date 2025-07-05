package com.vagas.vagas.jobs; // ou seu pacote de mappers

import com.vagas.vagas.jobs.JobVacancyEntity;
import com.vagas.vagas.jobs.dtos.JobVacancyResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobVacancyMapper {

    @Mapping(source = "company.id", target = "company.id")
    @Mapping(source = "company.user.name", target = "company.name")
    @Mapping(source = "recruiter.id", target = "recruiter.id")
    @Mapping(source = "recruiter.user.name", target = "recruiter.name")
    JobVacancyResponseDTO toResponseDTO(JobVacancyEntity jobVacancy);
}

