package br.com.rocketseat.job_management.modules.company.useCases;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_management.modules.company.dto.AuthCompanyDTO;
import br.com.rocketseat.job_management.modules.company.entities.CompanyEntity;
import br.com.rocketseat.job_management.modules.company.repositories.CompanyRepository;

@Service
public class AuthenticateCompanyUseCase {

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired 
  private PasswordEncoder passwordEncoder;

  public void execute(AuthCompanyDTO authCompany) throws AuthenticationException{
    CompanyEntity company = this.companyRepository.findByUsername(authCompany.getUsername())
      .orElseThrow(()-> new UsernameNotFoundException("Company not found"));

    boolean passwordMatches = this.passwordEncoder.matches(authCompany.getPassword(), company.getPassword());

    if(!passwordMatches){
      throw new AuthenticationException();
    }
  }

}
