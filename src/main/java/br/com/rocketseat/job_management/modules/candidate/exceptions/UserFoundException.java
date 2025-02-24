package br.com.rocketseat.job_management.modules.candidate.exceptions;

public class UserFoundException extends RuntimeException {
  public UserFoundException(){
    super("Usuário já Existe");
  }
}
