package be.technifutur.java.timairport.controller;

import be.technifutur.java.timairport.exception.RessourceNotFoundException;
import be.technifutur.java.timairport.exception.ValidationException;
import be.technifutur.java.timairport.model.dto.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(RessourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleRessourceNotFoundException(RessourceNotFoundException e, HttpServletRequest request) {
        ErrorDto errorDto = new ErrorDto(request.getMethod(),
                request.getRequestURL().toString(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDto> handleValidationException(ValidationException e, HttpServletRequest request) {
        ErrorDto errorDto = new ErrorDto(request.getMethod(),
                request.getRequestURL().toString(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }
}
