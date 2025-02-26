package br.com.rocketseat.job_management.providers;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JwtProvider {
  @Value("${security.token.secret}")
  private String secretKey;

  public String generateToken(String sub) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    String token = JWT.create()
        .withIssuer("javagas")
        .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
        .withSubject(sub).sign(algorithm);

    return token;
  }

  public String validateToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);
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
