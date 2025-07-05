package com.vagas.vagas.jobs;

import com.vagas.vagas.application.ApplicationEntity;
import com.vagas.vagas.company.CompanyEntity;
import com.vagas.vagas.enums.SENIORITY_LEVEL;
import com.vagas.vagas.recruiter.RecruiterEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "job_vacancy")
public class JobVacancyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Size(min = 1, max = 1007)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SENIORITY_LEVEL jobLevel;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @ManyToOne
    @JoinColumn(name = "recruiter_id", nullable = false)
    private RecruiterEntity recruiter;

    @OneToMany(
            mappedBy = "jobVacancy",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<ApplicationEntity> applications;

}
