package com.schoolplus.back.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import com.schoolplus.back.exception.InvalidTokenException;

@Service
public class TokenService {

  @Value("${api.security.token.secret:53cr3tSchoolPlu5}")
  private String secret;

  @Value("${api.security.token.expireHours:8}")
  private Long expireHours;

  public String buildToken(UserDetail usuario) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      String token = JWT.create()
          .withIssuer("API SchoolPlus")
          .withSubject(usuario.getEmail())
          .withExpiresAt(dataExpires())
          .sign(algorithm);
      return token;
    } catch (JWTCreationException exception) {
      new RuntimeException("Erro ao gerar o token", exception);
    }
    return null;
  }

  public String getSubject(String jwtToken) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
          .withIssuer("API SchoolPlus")
          .build()
          .verify(jwtToken)
          .getSubject();
    } catch (JWTVerificationException exception) {
      throw new InvalidTokenException();
    }
  }

  private Instant dataExpires() {
    return LocalDateTime.now().plusHours(expireHours).toInstant(ZoneOffset.of("-03:00"));
  }

}
