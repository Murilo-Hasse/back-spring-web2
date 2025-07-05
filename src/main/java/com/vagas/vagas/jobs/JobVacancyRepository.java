package com.vagas.vagas.jobs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JobVacancyRepository extends JpaRepository<JobVacancyEntity, UUID> {
}
