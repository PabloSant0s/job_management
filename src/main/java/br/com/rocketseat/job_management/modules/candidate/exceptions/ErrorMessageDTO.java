package br.com.rocketseat.job_management.modules.candidate.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorMessageDTO {
  private String message;
  private String field;
}
