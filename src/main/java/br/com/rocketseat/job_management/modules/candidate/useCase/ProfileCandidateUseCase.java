package br.com.rocketseat.job_management.modules.candidate.useCase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_management.exceptions.CandidateNotFoundException;
import br.com.rocketseat.job_management.modules.candidate.CandidateEntity;
import br.com.rocketseat.job_management.modules.candidate.CandidateRepository;
import br.com.rocketseat.job_management.modules.candidate.dto.ProfileCandidateDTO;

@Service
public class ProfileCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  public ProfileCandidateDTO execute(UUID candidateId) {
    CandidateEntity candidateEntity = candidateRepository.findById(candidateId).orElseThrow(() -> new CandidateNotFoundException());

    return ProfileCandidateDTO.builder()
        .id(candidateEntity.getId())
        .name(candidateEntity.getName())
        .username(candidateEntity.getUsername())
        .email(candidateEntity.getEmail())
        .description(candidateEntity.getDescription())
        .build();
  }
}
