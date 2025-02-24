package br.com.rocketseat.job_management.modules.candidate.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_management.modules.candidate.CandidateEntity;
import br.com.rocketseat.job_management.modules.candidate.CandidateRepository;
import br.com.rocketseat.job_management.modules.candidate.exceptions.UserFoundException;

@Service
public class CreateCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  public CandidateEntity execute(CandidateEntity candidate) {
    this.candidateRepository.findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail()).ifPresent(user->{
      throw new UserFoundException();
    });
    return candidateRepository.save(candidate);
  }
}
