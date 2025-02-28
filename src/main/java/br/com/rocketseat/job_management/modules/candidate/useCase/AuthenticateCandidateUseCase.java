package br.com.rocketseat.job_management.modules.candidate.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.rocketseat.job_management.dto.AuthResponseDTO;
import br.com.rocketseat.job_management.modules.candidate.CandidateEntity;
import br.com.rocketseat.job_management.modules.candidate.CandidateRepository;
import br.com.rocketseat.job_management.modules.candidate.dto.AuthCandidateDTO;
import br.com.rocketseat.job_management.providers.JwtCandidateProvider;

@Service
public class AuthenticateCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtCandidateProvider jwtProvider;


  public AuthResponseDTO execute(AuthCandidateDTO authCandidate) throws UsernameNotFoundException{
    CandidateEntity candidate = candidateRepository.findByUsername(authCandidate.username()).orElseThrow(()-> new UsernameNotFoundException("Username/password incorrect"));

    boolean passwordMatches = passwordEncoder.matches(authCandidate.password(), candidate.getPassword());

    if(!passwordMatches){
      throw new UsernameNotFoundException("Username/password incorrect");
    }

    AuthResponseDTO token = jwtProvider.generateToken(candidate.getId().toString());
    return token;
  }
}
