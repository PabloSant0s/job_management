package br.com.rocketseat.job_management.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_management.modules.candidate.exceptions.UserFoundException;
import br.com.rocketseat.job_management.modules.company.entities.CompanyEntity;
import br.com.rocketseat.job_management.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {
  @Autowired
  private CompanyRepository companyRepository;

  public CompanyEntity execute(CompanyEntity company) {
    this.companyRepository.findByUsernameOrEmail(company.getUsername(), company.getEmail()).ifPresent(user->{
      throw new UserFoundException();
    });

    return companyRepository.save(company);
  }
}
