package com.vagas.vagas.candidate;

import com.vagas.vagas.candidate.CandidateService;

import com.vagas.vagas.candidate.dtos.CandidateRegisterDTO;
import com.vagas.vagas.candidate.dtos.CandidateResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/candidate")
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping()
    public Set<CandidateResponseDTO> findAll() {
        Set<CandidateResponseDTO> candidates = new HashSet<>();
        candidates = candidateService.findAll();
        return candidates;
    }

    @GetMapping("/{id}")
    public CandidateResponseDTO findById(@PathVariable String id) {
        return candidateService.findById(UUID.fromString(id));
    }

}
