package br.com.rocketseat.job_management.modules.candidate.useCase;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rocketseat.job_management.exceptions.CandidateNotFoundException;
import br.com.rocketseat.job_management.exceptions.JobNotFoundException;
import br.com.rocketseat.job_management.modules.candidate.entities.ApplyJobEntity;
import br.com.rocketseat.job_management.modules.candidate.entities.CandidateEntity;
import br.com.rocketseat.job_management.modules.candidate.repositories.ApplyJobRepository;
import br.com.rocketseat.job_management.modules.candidate.repositories.CandidateRepository;
import br.com.rocketseat.job_management.modules.company.entities.JobEntity;
import br.com.rocketseat.job_management.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

  @InjectMocks
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private JobRepository jobRepository;

  @Mock
  private ApplyJobRepository applyJobRepository;

  @Test
  @DisplayName("Should not be apply jon with candidate not found")
  public void should_not_be_apply_jon_with_candidate_not_found() {
    try {
      applyJobCandidateUseCase.execute(null, null);
    } catch (Exception e) {
      assertInstanceOf(CandidateNotFoundException.class, e);
    }
  }

  @Test
  @DisplayName("Should not be apply jon with job not found")
  public void should_not_be_apply_jon_with_job_not_found() {
    UUID idCandidate = UUID.randomUUID();
    CandidateEntity candidate = new CandidateEntity();
    candidate.setId(idCandidate);
    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

    try {
      applyJobCandidateUseCase.execute(idCandidate, null);
    } catch (Exception e) {
      assertInstanceOf(JobNotFoundException.class, e);
    }
  }

  @Test
  @DisplayName("Should be able to create a new apply job")
  public void should_be_able_to_create_a_new_apply_job() {
    UUID idCandidate = UUID.randomUUID();
    UUID idJob = UUID.randomUUID();

    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
    when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));

    ApplyJobEntity applyJobEntity = ApplyJobEntity.builder()
        .candidateId(idCandidate)
        .jobId(idJob)
        .build();

    ApplyJobEntity applyJobEntityCreated = ApplyJobEntity.builder()
        .candidateId(idCandidate)
        .jobId(idJob)
        .id(UUID.randomUUID())
        .build();

    when(applyJobRepository.save(applyJobEntity)).thenReturn(applyJobEntityCreated);

    var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

    Assertions.assertThat(result).hasFieldOrProperty("id");
    assertNotNull(result.getId());
  }
}
