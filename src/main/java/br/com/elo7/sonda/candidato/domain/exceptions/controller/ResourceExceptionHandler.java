package br.com.elo7.sonda.candidato.domain.exceptions.controller;

import br.com.elo7.sonda.candidato.domain.exceptions.type.BusinessException;
import br.com.elo7.sonda.candidato.domain.exceptions.type.InternalErrorException;
import br.com.elo7.sonda.candidato.domain.exceptions.type.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Validation error", System.currentTimeMillis());

        for (FieldError x : e.getBindingResult().getFieldErrors()) {
            err.addError(x.getField(), x.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> dataIntegrity(BusinessException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<StandardError> internalErrorException(InternalErrorException e, HttpServletRequest request) {
        StandardError err = new StandardError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}
