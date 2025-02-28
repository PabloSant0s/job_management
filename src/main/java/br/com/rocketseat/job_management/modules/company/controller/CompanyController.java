package br.com.rocketseat.job_management.modules.company.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rocketseat.job_management.dto.AuthResponseDTO;
import br.com.rocketseat.job_management.exceptions.UserFoundException;
import br.com.rocketseat.job_management.modules.company.dto.AuthCompanyDTO;
import br.com.rocketseat.job_management.modules.company.entities.CompanyEntity;
import br.com.rocketseat.job_management.modules.company.useCases.AuthenticateCompanyUseCase;
import br.com.rocketseat.job_management.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/company")
public class CompanyController {

  @Autowired
  private CreateCompanyUseCase createCompanyUseCase;

  @Autowired
  private AuthenticateCompanyUseCase authenticateCompanyUseCase;
  @PostMapping
  public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity company) {
      try {
        var result = this.createCompanyUseCase.execute(company);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
      } catch (UserFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
  }

  @PostMapping("/auth")
  public ResponseEntity<Object> authCompany(@RequestBody AuthCompanyDTO authCompanyDTO) {
    try {
      AuthResponseDTO token = authenticateCompanyUseCase.execute(authCompanyDTO);
      return ResponseEntity.ok(token);
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
  
}
