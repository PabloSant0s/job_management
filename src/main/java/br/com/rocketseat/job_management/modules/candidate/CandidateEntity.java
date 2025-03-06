package br.com.rocketseat.job_management.modules.candidate;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "tb_cantidate")
public class CandidateEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
   @Schema(example = "Daniel de Souza", requiredMode = RequiredMode.REQUIRED, description = "Nome do candidato")
  private String name;
  @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaços")
  @Schema(example = "daniel", requiredMode = RequiredMode.REQUIRED, description = "Username do candidato")
  private String username;
  @Email(message = "O campo [email] deve conter um e-mail válido")
  @Schema(example = "daniel@gmail.com", requiredMode = RequiredMode.REQUIRED, description = "E-mail do candidato")
  private String email;
  @Length(min = 10, max = 100, message = "A senha deve conter entre 10 e 100 caracteres")
  @Schema(example = "admin@1234", minLength = 10, maxLength = 100, requiredMode = RequiredMode.REQUIRED, description = "Senha do candidato")
  private String password;
  private String description;
  @Schema(example = "Desenvolvedor Java", requiredMode = RequiredMode.REQUIRED, description = "Breve descrição do candidato")
  private String curriculum;

  @Column(name = "created_at")
  @CreationTimestamp
  private Instant createdAt;
}
