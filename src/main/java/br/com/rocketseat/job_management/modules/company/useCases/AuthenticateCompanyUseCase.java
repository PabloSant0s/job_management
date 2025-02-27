package br.com.rocketseat.job_management.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_management.modules.company.dto.AuthCompanyDTO;
import br.com.rocketseat.job_management.modules.company.entities.CompanyEntity;
import br.com.rocketseat.job_management.modules.company.repositories.CompanyRepository;
import br.com.rocketseat.job_management.providers.JwtProvider;

@Service
public class AuthenticateCompanyUseCase {

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public String execute(AuthCompanyDTO authCompany) throws UsernameNotFoundException {
    CompanyEntity company = this.companyRepository.findByUsername(authCompany.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("Username/password incorrect"));

    boolean passwordMatches = this.passwordEncoder.matches(authCompany.getPassword(), company.getPassword());

    if (!passwordMatches) {
      throw new UsernameNotFoundException("Username/password incorrect");
    }
    String token = this.jwtProvider.generateToken(company.getId().toString(), false);

    return token;
  }

}
