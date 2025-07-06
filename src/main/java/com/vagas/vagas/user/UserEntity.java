package com.vagas.vagas.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idUser;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 63)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private USER_ROLE role;

    // Métodos da interface UserDetails do Spring Security

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == null) {
            // Log para quando a role é nula
            System.out.println("!!! ATENÇÃO: A role do utilizador " + this.email + " é nula. Nenhuma autoridade será concedida.");
            return List.of();
        }

        // Cria a lista de autoridades
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));

        // --- INÍCIO DO LOG DE DEPURACÃO ---
        // Imprime na consola as autoridades que estão a ser retornadas para este utilizador
        System.out.println("---[DEBUG - getAuthorities]---");
        System.out.println("Utilizador: " + this.email);
        System.out.println("Role no Banco: " + this.role.name());
        System.out.println("Autoridades retornadas para o Spring Security: " + authorities);
        System.out.println("-----------------------------");
        // --- FIM DO LOG DE DEPURACÃO ---

        return authorities;
    }

    @Override
    public String getUsername() {
        // O Spring Security usará o email como nome de usuário para login.
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // A conta nunca expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // A conta nunca é bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // As credenciais nunca expiram
    }

    @Override
    public boolean isEnabled() {
        return true; // A conta está sempre habilitada
    }
}
