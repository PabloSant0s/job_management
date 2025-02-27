package br.com.rocketseat.job_management.providers;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JwtProvider {
  @Value("${security.token.secret}")
  private String secretKey;

  @Value("${security.token.secret.candidate}")
  private String secretKeyCandidate;

  public String generateToken(String sub, boolean isCandidate) {
    Algorithm algorithm = isCandidate ? Algorithm.HMAC256(secretKeyCandidate) : Algorithm.HMAC256(secretKey);
    String token = isCandidate ? JWT.create()
        .withIssuer("javagas")
        .withExpiresAt(Instant.now().plus(Duration.ofMinutes(10)))
        .withClaim("roles", Arrays.asList("candidate"))
        .withSubject(sub).sign(algorithm)
        : JWT.create()
            .withIssuer("javagas")
            .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
            .withSubject(sub).sign(algorithm);

    return token;
  }

  public String validateToken(String token, boolean isCandidate) {
    Algorithm algorithm = isCandidate ? Algorithm.HMAC256(secretKeyCandidate) : Algorithm.HMAC256(secretKey);
    ;
    try {
      token = token.replace("Bearer ", "");
      String subject = JWT.require(algorithm).build().verify(token).getSubject();
      return subject;
    } catch (JWTVerificationException e) {
      e.printStackTrace();
      return null;
    }
  }
}
