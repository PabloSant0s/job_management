package br.com.rocketseat.job_management.modules.candidate.useCase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_management.exceptions.CandidateNotFoundException;
import br.com.rocketseat.job_management.exceptions.JobNotFoundException;
import br.com.rocketseat.job_management.modules.candidate.CandidateRepository;
import br.com.rocketseat.job_management.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JobRepository jobRepository;
  public void execute(UUID idCandidate, UUID idJob){
    candidateRepository.findById(idCandidate).orElseThrow(() -> {
      throw new CandidateNotFoundException();
    });

    jobRepository.findById(idJob).orElseThrow(() -> {
      throw new JobNotFoundException();
    });
  }
}
