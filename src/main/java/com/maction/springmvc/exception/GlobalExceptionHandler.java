package com.maction.springmvc.exception;

import com.maction.springmvc.model.rest.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(Exception e) {
        var response = new ErrorResponse();
        response.setMessage(e.getMessage());
        response.setStatus(400);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(Exception e) {
        var response = new ErrorResponse();
        response.setMessage(e.getMessage());
        response.setStatus(404);
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        var response = new ErrorResponse();
        response.setMessage(e.getMessage());
        response.setStatus(500);
        return ResponseEntity.internalServerError().body(response);
    }
}
