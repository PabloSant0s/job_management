package br.com.rocketseat.job_management.modules.candidate.useCase;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rocketseat.job_management.exceptions.CandidateNotFoundException;
import br.com.rocketseat.job_management.modules.candidate.CandidateRepository;
import br.com.rocketseat.job_management.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

  @InjectMocks
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private JobRepository jobRepository;

  @Test
  @DisplayName("Should not be apply jon with candidate not found")
  public void should_not_be_apply_jon_with_candidate_not_found(){
    try {
      applyJobCandidateUseCase.execute(null, null);
    } catch (Exception e) {
      assertInstanceOf(CandidateNotFoundException.class, e);
    }
  }
}
