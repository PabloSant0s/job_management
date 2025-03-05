package br.com.rocketseat.job_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI openAPI(){
    return new OpenAPI()
     .info(new io.swagger.v3.oas.models.info.Info().title("Job Management API")
       .version("1.0.0")
       .description("API para gerenciamento de vagas e candidatos."))
       .schemaRequirement("jwt_auth", createSecurityScheme());
  }

  private SecurityScheme createSecurityScheme(){
    return new SecurityScheme()
    .name("jwt_auth")
     .type(SecurityScheme.Type.HTTP)
     .scheme("bearer")
     .bearerFormat("JWT")
     .in(SecurityScheme.In.HEADER);
  }
}
