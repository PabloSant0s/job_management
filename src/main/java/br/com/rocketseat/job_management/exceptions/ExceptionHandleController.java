package br.com.rocketseat.job_management.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
public class ExceptionHandleController {

  private MessageSource messageSource;

  public ExceptionHandleController(MessageSource message){
    this.messageSource = message;
  }
  
  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity<List<ErrorMessageDTO>> handleWebExchangeBindException(WebExchangeBindException e){
    List<ErrorMessageDTO> errors = new ArrayList<ErrorMessageDTO>();

    e.getBindingResult().getFieldErrors().forEach(err->{
      String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
      ErrorMessageDTO error = new ErrorMessageDTO(message, err.getField());
      errors.add(error);
    });

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
}
