package br.com.rocketseat.job_management.exceptions;

public class UserFoundException extends RuntimeException {
  public UserFoundException(){
    super("Usuário já Existe");
  }
}
