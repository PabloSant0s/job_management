package br.com.rocketseat.job_management.providers;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.rocketseat.job_management.dto.AuthResponseDTO;

@Service
public class JwtCompanyProvider implements JwtProvider {
  @Value("${security.token.secret}")
  private String secretKey;

  @Override
  public AuthResponseDTO generateToken(String sub) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    Instant expiresIn = Instant.now().plus(Duration.ofHours(2));
    String token = JWT.create()
        .withIssuer("javagas")
        .withExpiresAt(expiresIn)
        .withSubject(sub).sign(algorithm);

    return AuthResponseDTO.builder().access_token(token).expires_in(expiresIn.toEpochMilli()).build();
  }

  @Override
  public Optional<DecodedJWT> validateToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    try {
      token = token.replace("Bearer ", "");
      DecodedJWT paylod = JWT.require(algorithm).build().verify(token);
      return Optional.of(paylod);
    } catch (JWTVerificationException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }
}
