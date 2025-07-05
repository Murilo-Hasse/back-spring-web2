package com.vagas.vagas.auth;

import com.vagas.vagas.auth.dtos.LoginRequestDTO;
import com.vagas.vagas.auth.dtos.LoginResponseDTO;
import com.vagas.vagas.candidate.CandidateService;
import com.vagas.vagas.candidate.dtos.CandidateRegisterDTO;
import com.vagas.vagas.candidate.dtos.CandidateResponseDTO;
import com.vagas.vagas.company.CompanyService;
import com.vagas.vagas.company.dtos.CompanyRegisterDTO;
import com.vagas.vagas.company.dtos.CompanyResponseDTO;
import com.vagas.vagas.config.security.JwtService;
import com.vagas.vagas.recruiter.RecruiterService;
import com.vagas.vagas.recruiter.dtos.RecruiterRegisterDTO;
import com.vagas.vagas.recruiter.dtos.RecruiterResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    private final CandidateService candidateService;
    private final CompanyService companyService;
    private final RecruiterService recruiterService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        // O AuthenticationManager usa o nosso UserDetailsService e PasswordEncoder para validar o usuário
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        // Se a autenticação passar, busca o usuário e gera o token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
        final String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/candidate/register")
    public ResponseEntity<CandidateResponseDTO> registerCandidate(@RequestBody CandidateRegisterDTO dto) {
        CandidateResponseDTO response = candidateService.registerCandidate(dto);
        // Retorna 201 Created, o status correto para criação de recurso.
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/company/register")
    public ResponseEntity<CompanyResponseDTO> registerCompany(@RequestBody CompanyRegisterDTO dto) {
        CompanyResponseDTO response = companyService.registerCompany(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/recruiter/register")
    public ResponseEntity<RecruiterResponseDTO> registerRecruiter(@RequestBody RecruiterRegisterDTO dto) {
        RecruiterResponseDTO response = recruiterService.registerRecruiter(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
