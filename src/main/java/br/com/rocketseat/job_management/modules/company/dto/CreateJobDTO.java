package br.com.rocketseat.job_management.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobDTO {
    @NotBlank(message = "Description Obrigatório")
     @Schema(example = "Vaga para pessoa desenvolvedora júnior", requiredMode = RequiredMode.REQUIRED)
    private String description;
    @NotBlank(message = "Benefícios Obrigatório")
    @Schema(example = "GymPass, Plano de saúde", requiredMode = RequiredMode.REQUIRED)
    private String benefits;
    @NotBlank(message = "Level Obrigatório")
    @Schema(example = "JUNIOR", requiredMode = RequiredMode.REQUIRED)
    private String level;
}
