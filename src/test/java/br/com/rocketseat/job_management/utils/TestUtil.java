package br.com.rocketseat.job_management.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {
  public static String objectToJson(Object obj) throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(obj);
  }

  public static String generateToken(UUID idCompany, String secret){
    Algorithm algorithm = Algorithm.HMAC256(secret);
    Instant expiresIn = Instant.now().plus(Duration.ofHours(2));
    return JWT.create()
        .withIssuer("javagas")
        .withExpiresAt(expiresIn)
        .withClaim("roles", Arrays.asList("COMPANY"))
        .withSubject(idCompany.toString()).sign(algorithm);
  }
}
