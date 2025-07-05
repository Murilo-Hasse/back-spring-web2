package com.vagas.vagas.company;

import com.vagas.vagas.jobs.JobVacancyEntity;
import com.vagas.vagas.recruiter.RecruiterEntity;
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
@Table(name = "company_profile") // Nome da tabela alterado para clareza
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Campos de autenticação (email, password, name, phone) foram MOVIDOS para UserEntity

    @Column(nullable = false, length = 63)
    private String socialNumber; // CNPJ

    @Column(nullable = false)
    private String address;

    // RELACIONAMENTO ONE-TO-ONE com a entidade User
    @Delegate
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "idUser", nullable = false)
    private UserEntity user;

    @ManyToMany(mappedBy = "companiesRepresented")
    private List<RecruiterEntity> recruiters;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<JobVacancyEntity> jobs;

    public CompanyEntity() {
        jobs = new ArrayList<>();
        recruiters = new ArrayList<>();
    }
}
