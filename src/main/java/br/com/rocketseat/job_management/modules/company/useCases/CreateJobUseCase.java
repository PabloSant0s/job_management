package br.com.rocketseat.job_management.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_management.modules.company.entities.JobEntity;
import br.com.rocketseat.job_management.modules.company.exceptions.CompanyNotFoundException;
import br.com.rocketseat.job_management.modules.company.repositories.CompanyRepository;
import br.com.rocketseat.job_management.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {
  @Autowired
  private JobRepository jobRepository;

  @Autowired 
  private CompanyRepository companyRepository;

  public void execute(JobEntity job) {
    boolean existCompany = this.companyRepository.findById(job.getCompanyId()).isPresent();

    if(!existCompany){
      throw new CompanyNotFoundException();
    }

    this.jobRepository.save(job);
  }
}
