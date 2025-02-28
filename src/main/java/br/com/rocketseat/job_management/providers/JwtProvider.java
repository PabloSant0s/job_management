package br.com.rocketseat.job_management.providers;

import java.util.Optional;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.rocketseat.job_management.dto.AuthResponseDTO;

public interface JwtProvider {
  AuthResponseDTO generateToken(String sub);

  Optional<DecodedJWT> validateToken(String token);
}
