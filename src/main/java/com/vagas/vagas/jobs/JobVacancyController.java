package com.vagas.vagas.jobs;

import com.vagas.vagas.jobs.dtos.JobVacancyCreateDTO;
import com.vagas.vagas.jobs.dtos.JobVacancyResponseDTO;
import jakarta.servlet.annotation.HttpConstraint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job")
public class JobVacancyController {

//    private final JobVacancyService jobVacancyService;
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    public ResponseEntity<JobVacancyResponseDTO> save(@RequestBody JobVacancyCreateDTO) {
//        return jobVacancyService.create(JobVacancyCreateDTO);
//    }
}
