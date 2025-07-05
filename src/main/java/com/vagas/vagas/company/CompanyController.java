package com.vagas.vagas.company;

import com.vagas.vagas.company.dtos.CompanyRegisterDTO;
import com.vagas.vagas.company.dtos.CompanyResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @GetMapping()
    public Set<CompanyResponseDTO> findAll() {
        Set<CompanyResponseDTO> companies = new HashSet<>();
        companies = companyService.findAll();
        return companies;
    }

    @GetMapping("/{id}")
    public CompanyResponseDTO findById(@PathVariable String id) {
        return companyService.findById(UUID.fromString(id));
    }

}
