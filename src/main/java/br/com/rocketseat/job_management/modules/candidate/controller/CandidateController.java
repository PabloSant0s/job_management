package br.com.rocketseat.job_management.modules.candidate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rocketseat.job_management.modules.candidate.CandidateEntity;
import br.com.rocketseat.job_management.modules.candidate.CandidateRepository;
import br.com.rocketseat.job_management.modules.candidate.exceptions.UserFoundException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/candidate")
public class CandidateController {
  
  @Autowired
  private CandidateRepository candidateRepository;
  @PostMapping
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate) {
      this.candidateRepository.findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail()).ifPresent(user->{
        throw new UserFoundException();
      });
      
      return ResponseEntity.status(HttpStatus.CREATED).body(candidate);
  }
  
}
