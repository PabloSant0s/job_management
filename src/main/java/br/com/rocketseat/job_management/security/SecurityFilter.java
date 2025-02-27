package br.com.rocketseat.job_management.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.rocketseat.job_management.providers.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private JwtProvider jwtProvider;

  @SuppressWarnings("null")
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    SecurityContextHolder.getContext().setAuthentication(null);

    String token = request.getHeader("Authorization");
    if (token != null) {
      String subject = this.jwtProvider.validateToken(token);

      if (subject == null) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalido");
        return;
      }

      request.setAttribute("company_id", subject);
      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subject, null,
          Collections.emptyList());

      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);
  }

}
