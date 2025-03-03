package br.com.rocketseat.job_management.modules.candidate.useCase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_management.modules.company.entities.JobEntity;
import br.com.rocketseat.job_management.modules.company.repositories.JobRepository;

@Service
public class ListAllJobsByFilterUseCase {
  @Autowired
  private JobRepository jobRepository;
  public List<JobEntity> execute(String description){
    return jobRepository.findByDescriptionContainingIgnoreCase(description);
  }
}
