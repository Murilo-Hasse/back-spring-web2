package com.vagas.vagas.candidate;

import com.vagas.vagas.application.ApplicationEntity;
import com.vagas.vagas.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "candidate_profile") // Nome da tabela alterado para clareza
public class CandidateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(length = 63)
    private String socialNumber; // CPF

    @Column(nullable = false)
    private LocalDate birthdate;

    // RELACIONAMENTO ONE-TO-ONE com a entidade User

    @Delegate(excludes = UserDetails.class)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "idUser", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ApplicationEntity> applications;


    public CandidateEntity() {
        this.applications = new ArrayList<>();
    }

}
