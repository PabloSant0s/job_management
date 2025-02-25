package br.com.rocketseat.job_management.modules.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rocketseat.job_management.modules.company.entities.JobEntity;
import br.com.rocketseat.job_management.modules.company.exceptions.CompanyNotFoundException;
import br.com.rocketseat.job_management.modules.company.useCases.CreateJobUseCase;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/job")
public class JobController {
  @Autowired 
  private CreateJobUseCase createJobUseCase;

  @PostMapping("")
  public ResponseEntity<Object> create(@Valid @RequestBody JobEntity job) {
      try {
        this.createJobUseCase.execute(job);
        return ResponseEntity.noContent().build();
      } catch (CompanyNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
  }
  
}
