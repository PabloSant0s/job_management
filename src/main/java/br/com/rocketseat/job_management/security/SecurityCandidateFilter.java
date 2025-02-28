package br.com.rocketseat.job_management.security;

import java.io.IOException;
import java.util.Collections;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.rocketseat.job_management.providers.JwtCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter {

  @Autowired
  private JwtCandidateProvider jwtProvider;

  @SuppressWarnings("null")
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    SecurityContextHolder.getContext().setAuthentication(null);

    String token = request.getHeader("Authorization");
    if (token != null  && request.getRequestURI().startsWith("/candidate")) {
      token = token.replace("Bearer ", "");
      try {
        DecodedJWT payload = jwtProvider.validateToken(token)
            .orElseThrow(() -> new AuthenticationException("Invalid token"));
        request.setAttribute("candidate_id", payload.getSubject());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(payload.getSubject(), null,
            Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);
      } catch (Exception e) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalido");
      }
    }

    filterChain.doFilter(request, response);
  }

}
