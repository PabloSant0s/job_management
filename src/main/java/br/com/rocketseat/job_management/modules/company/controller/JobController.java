package br.com.rocketseat.job_management.modules.company.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rocketseat.job_management.exceptions.CompanyNotFoundException;
import br.com.rocketseat.job_management.modules.company.dto.CreateJobDTO;
import br.com.rocketseat.job_management.modules.company.entities.JobEntity;
import br.com.rocketseat.job_management.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/job")
public class JobController {
  @Autowired 
  private CreateJobUseCase createJobUseCase;

  @PostMapping("")
  public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO jobDTO, HttpServletRequest request) {
      try {
        JobEntity jobEntity = JobEntity.builder()
                                .description(jobDTO.getDescription())
                                .benefits(jobDTO.getBenefits())
                                .level(jobDTO.getLevel())
                                .companyId(UUID.fromString(request.getAttribute("company_id").toString()))
                                .build();
        this.createJobUseCase.execute(jobEntity);
        return ResponseEntity.noContent().build();
      } catch (CompanyNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
  }
  
}
