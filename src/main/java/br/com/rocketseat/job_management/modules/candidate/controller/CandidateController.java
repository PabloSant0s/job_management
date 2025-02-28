package br.com.rocketseat.job_management.modules.candidate.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rocketseat.job_management.exceptions.CandidateNotFoundException;
import br.com.rocketseat.job_management.exceptions.UserFoundException;
import br.com.rocketseat.job_management.modules.candidate.CandidateEntity;
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
  @PostMapping
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate) {
      try {
        return new ResponseEntity<>(this.createCandidateUseCase.execute(candidate),HttpStatus.CREATED);
      } catch (UserFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
  }

  @GetMapping
  public ResponseEntity<Object> getProfile(HttpServletRequest request) {
    try {
        UUID candidateId = UUID.fromString(request.getAttribute("candidate_id").toString());
        return ResponseEntity.ok().body(profileCandidateUseCase.execute(candidateId));
      } catch (CandidateNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
  }
}
