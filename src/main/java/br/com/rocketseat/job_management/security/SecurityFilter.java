package br.com.rocketseat.job_management.security;

import java.io.IOException;
import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.rocketseat.job_management.providers.JwtCompanyProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private JwtCompanyProvider jwtProvider;

  @SuppressWarnings("null")
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    String token = request.getHeader("Authorization");
    if (token != null && request.getRequestURI().startsWith("/company")) {
      token = token.replace("Bearer ", "");
      try {
        DecodedJWT payload = this.jwtProvider.validateToken(token)
            .orElseThrow(() -> new AuthenticationException("Invalid token"));

        List<SimpleGrantedAuthority> grants = payload.getClaim("roles").asList(String.class).stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())).toList();

        request.setAttribute("company_id", payload.getSubject());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(payload.getSubject(), null,
            grants);

        SecurityContextHolder.getContext().setAuthentication(auth);
      } catch (Exception e) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalido");
      }
    }

    filterChain.doFilter(request, response);
  }

}
