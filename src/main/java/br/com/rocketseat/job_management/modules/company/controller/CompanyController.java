package br.com.rocketseat.job_management.modules.company.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rocketseat.job_management.modules.candidate.exceptions.UserFoundException;
import br.com.rocketseat.job_management.modules.company.entities.CompanyEntity;
import br.com.rocketseat.job_management.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/company")
public class CompanyController {

  @Autowired
  private CreateCompanyUseCase createCompanyUseCase;
  @PostMapping
  public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity company) {
      try {
        var result = this.createCompanyUseCase.execute(company);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
      } catch (UserFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
  }
  
}
