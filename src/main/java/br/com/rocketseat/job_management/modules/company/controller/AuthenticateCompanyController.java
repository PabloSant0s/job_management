package br.com.rocketseat.job_management.modules.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rocketseat.job_management.dto.AuthResponseDTO;
import br.com.rocketseat.job_management.modules.company.dto.AuthCompanyDTO;
import br.com.rocketseat.job_management.modules.company.useCases.AuthenticateCompanyUseCase;

@RestController
@RequestMapping("/auth")
public class AuthenticateCompanyController {

  @Autowired
  private AuthenticateCompanyUseCase authenticateCompanyUseCase;

  @PostMapping("/company")
  public ResponseEntity<Object> authCompany(@RequestBody AuthCompanyDTO authCompanyDTO) {
    try {
      AuthResponseDTO token = authenticateCompanyUseCase.execute(authCompanyDTO);
      return ResponseEntity.ok(token);
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

}
