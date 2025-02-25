package br.com.rocketseat.job_management.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_management.modules.company.entities.CompanyEntity;
import br.com.rocketseat.job_management.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {
  @Autowired
  private CompanyRepository companyRepository;

  @Autowired PasswordEncoder passwordEncoder;

  public CompanyEntity execute(CompanyEntity company) {
    this.companyRepository.findByUsernameOrEmail(company.getUsername(), company.getEmail()).ifPresent(user->{
      throw new UsernameNotFoundException("Company not found");
    });

    company.setPassword(passwordEncoder.encode(company.getPassword()));

    return companyRepository.save(company);
  }
}
