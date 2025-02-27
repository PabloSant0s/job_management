package br.com.rocketseat.job_management.modules.candidate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rocketseat.job_management.dto.AuthResponseDTO;
import br.com.rocketseat.job_management.modules.candidate.dto.AuthCandidateDTO;
import br.com.rocketseat.job_management.modules.candidate.useCase.AuthenticateCandidateUseCase;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthenticateCandidateController {
  @Autowired
  private AuthenticateCandidateUseCase authenticateCandidateUseCase;

  @PostMapping("/candidate")
  public ResponseEntity<Object> authCandidate (@RequestBody AuthCandidateDTO authCandidateDTO) {
      try {
        AuthResponseDTO result = authenticateCandidateUseCase.execute(authCandidateDTO);
        return ResponseEntity.ok().body(result);
      } catch (UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
      }
  } 
}
