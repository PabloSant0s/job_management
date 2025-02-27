package br.com.rocketseat.job_management.modules.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateJobDTO {
    @NotBlank(message = "Description Obrigatório")
    private String description;
    @NotBlank(message = "Benefícios Obrigatório")
    private String benefits;
    @NotBlank(message = "Level Obrigatório")
    private String level;
}
