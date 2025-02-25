package br.com.rocketseat.job_management.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.rocketseat.job_management.modules.company.dto.AuthCompanyDTO;
import br.com.rocketseat.job_management.modules.company.entities.CompanyEntity;
import br.com.rocketseat.job_management.modules.company.repositories.CompanyRepository;

@Service
public class AuthenticateCompanyUseCase {

  @Value("${security.token.secret}")
  private String secretKey;

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

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    String token = JWT.create().withIssuer("javagas").withSubject(company.getId().toString()).sign(algorithm);

    return token;
  }

}
