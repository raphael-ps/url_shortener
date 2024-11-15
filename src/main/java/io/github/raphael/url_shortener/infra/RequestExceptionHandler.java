package io.github.raphael.url_shortener.infra;


import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException exception){
        String message = "An error occurred while processing the request.\n";

        if (exception.getCause() instanceof ConstraintViolationException) {
            message += "Nickname already exists. Please choose another nickname.";
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }
}
