package com.vagas.vagas.recruiter;

import com.vagas.vagas.recruiter.dtos.RecruiterRegisterDTO;
import com.vagas.vagas.recruiter.dtos.RecruiterResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/recruiter")
public class RecruiterController {

    private final RecruiterService recruiterService;

    @Autowired
    public RecruiterController(RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }


    @GetMapping()
    public Set<RecruiterResponseDTO> findAll() {
        Set<RecruiterResponseDTO> recruiters = new HashSet<>();
        recruiters = recruiterService.findAll();
        return recruiters;
    }

    @GetMapping("/{id}")
    public RecruiterResponseDTO findById(@PathVariable String id) {
        return recruiterService.findById(UUID.fromString(id));
    }

}
