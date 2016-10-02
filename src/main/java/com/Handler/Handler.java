package com.Handler;

import com.Exception.ConstraintViolationException;
import com.Exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Handler {

    @ExceptionHandler({ResourceNotFoundException.class, ConstraintViolationException.class})
    public ResponseEntity<String> handler(Exception e) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus status = HttpStatus.NOT_FOUND;
        String msg = e.getMessage();

        if (e instanceof ConstraintViolationException) {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(msg, headers, status);
    }
}
