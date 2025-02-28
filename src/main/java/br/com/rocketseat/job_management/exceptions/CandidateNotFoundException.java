package br.com.rocketseat.job_management.exceptions;

public class CandidateNotFoundException extends RuntimeException {
  public CandidateNotFoundException(){
    super("Candidato não encontrado");
  }
}
