package com.example.account.management.accountmanager.rest;

import com.example.account.management.accountmanager.api.ErrorResponse;
import com.example.account.management.accountmanager.service.AccountsNotFoundException;
import com.example.account.management.accountmanager.service.CustomerNotFoundException;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { CustomerNotFoundException.class, AccountsNotFoundException.class })
    public ResponseEntity<String> handleResourceNotFoundException(final RuntimeException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<String> handleIllegalArgumentException(final CustomerNotFoundException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
