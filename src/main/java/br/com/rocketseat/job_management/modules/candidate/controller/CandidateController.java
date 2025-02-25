package br.com.rocketseat.job_management.modules.candidate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rocketseat.job_management.exceptions.UserFoundException;
import br.com.rocketseat.job_management.modules.candidate.CandidateEntity;
import br.com.rocketseat.job_management.modules.candidate.useCase.CreateCandidateUseCase;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/candidate")
public class CandidateController {
  
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;;
  @PostMapping
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate) {
      try {
        return new ResponseEntity<>(this.createCandidateUseCase.execute(candidate),HttpStatus.CREATED);
      } catch (UserFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
  }
  
}
