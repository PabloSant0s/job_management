package br.com.rocketseat.job_management.modules.candidate.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_management.exceptions.UserFoundException;
import br.com.rocketseat.job_management.modules.candidate.CandidateEntity;
import br.com.rocketseat.job_management.modules.candidate.CandidateRepository;
import br.com.rocketseat.job_management.modules.candidate.dto.ProfileCandidateDTO;

@Service
public class CreateCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public ProfileCandidateDTO execute(CandidateEntity candidate) {
    this.candidateRepository.findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail()).ifPresent(user -> {
      throw new UserFoundException();
    });

    candidate.setPassword(passwordEncoder.encode(candidate.getPassword()));

    candidate = candidateRepository.save(candidate);
    return ProfileCandidateDTO.builder()
        .id(candidate.getId())
        .name(candidate.getName())
        .username(candidate.getUsername())
        .email(candidate.getEmail())
        .description(candidate.getDescription())
        .build();
  }
}
