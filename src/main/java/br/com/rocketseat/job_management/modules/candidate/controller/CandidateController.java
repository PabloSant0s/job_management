package br.com.rocketseat.job_management.modules.candidate.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rocketseat.job_management.dto.AuthResponseDTO;
import br.com.rocketseat.job_management.exceptions.CandidateNotFoundException;
import br.com.rocketseat.job_management.exceptions.UserFoundException;
import br.com.rocketseat.job_management.modules.candidate.CandidateEntity;
import br.com.rocketseat.job_management.modules.candidate.dto.AuthCandidateDTO;
import br.com.rocketseat.job_management.modules.candidate.useCase.AuthenticateCandidateUseCase;
import br.com.rocketseat.job_management.modules.candidate.useCase.CreateCandidateUseCase;
import br.com.rocketseat.job_management.modules.candidate.useCase.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/candidate")
public class CandidateController {
  
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;;

  @Autowired 
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private AuthenticateCandidateUseCase authenticateCandidateUseCase;
  @PostMapping
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate) {
      try {
        return new ResponseEntity<>(this.createCandidateUseCase.execute(candidate),HttpStatus.CREATED);
      } catch (UserFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
  }

  @PreAuthorize("hasRole('CANDIDATE')")
  @GetMapping
  public ResponseEntity<Object> getProfile(HttpServletRequest request) {
    try {
        UUID candidateId = UUID.fromString(request.getAttribute("candidate_id").toString());
        return ResponseEntity.ok().body(profileCandidateUseCase.execute(candidateId));
      } catch (CandidateNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
  }

  @PostMapping("/auth")
  public ResponseEntity<Object> authCandidate (@RequestBody AuthCandidateDTO authCandidateDTO) {
      try {
        AuthResponseDTO result = authenticateCandidateUseCase.execute(authCandidateDTO);
        return ResponseEntity.ok().body(result);
      } catch (UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
      }
  } 
}
