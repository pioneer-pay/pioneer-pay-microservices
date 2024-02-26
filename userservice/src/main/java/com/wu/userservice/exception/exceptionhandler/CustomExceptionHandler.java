package com.wu.userservice.exception.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wu.userservice.exception.AlreadyExistsException;
import com.wu.userservice.exception.InvalidCredentialsException;
import com.wu.userservice.exception.ResourceNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler {
    
    @ResponseBody
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String>handleAlreadyExistsException(AlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String>handleInvalidCredencialEntity(InvalidCredentialsException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String>handleResourceNotFoundException(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
