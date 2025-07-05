package com.vagas.vagas.recruiter;

import com.vagas.vagas.company.CompanyEntity;
import com.vagas.vagas.jobs.JobVacancyEntity;
import com.vagas.vagas.user.UserEntity; // Importe a nova entidade User
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "recruiter_profile") // Nome da tabela alterado para clareza
public class RecruiterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Campos de autenticação (email, password, name, phone) foram MOVIDOS para UserEntity

    // RELACIONAMENTO ONE-TO-ONE com a entidade User
    @Delegate
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "idUser", nullable = false)
    private UserEntity user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "companies_recruiters",
            joinColumns = @JoinColumn(name = "recruiter_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<CompanyEntity> companiesRepresented;

    @OneToMany(mappedBy = "recruiter", cascade = CascadeType.ALL)
    private List<JobVacancyEntity> vacancies;

    public RecruiterEntity() {
        companiesRepresented = new ArrayList<>();
        vacancies = new ArrayList<>();
    }
}