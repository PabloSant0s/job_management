package br.com.rocketseat.job_management.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateDTO {
  private UUID id;
  @Schema(example = "maria")
  private String name;
  @Schema(example = "Maria de Souza")
  private String username;
  @Schema(example = "maria@gmail.com")
  private String email;
  @Schema(example = "Desenvolvedora Java")
  private String description;
}
