package br.com.rocketseat.job_management.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  private final String[] SWAGGER_LIST = {
    "/swagger-ui/**",
    "/v3/api-docs/**",
    "/swagger-resources/**"
  };

  @Autowired
  private SecurityFilter securityFilter;

  @Autowired 
  private SecurityCandidateFilter securityCandidateFilter;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> {
      auth.requestMatchers("/candidate").permitAll();
      auth.requestMatchers("/company").permitAll();
      auth.requestMatchers("/company/auth").permitAll();
      auth.requestMatchers("/candidate/auth").permitAll();
      auth.requestMatchers(SWAGGER_LIST).permitAll();
      auth.anyRequest().authenticated();
    })
        .addFilterBefore(securityFilter, BasicAuthenticationFilter.class)
        .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)
        .build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}