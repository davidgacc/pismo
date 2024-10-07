package com.pismo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value= AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException ex){
        log.error(HttpStatus.NOT_FOUND + ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(value= AccountAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAccountAlreadyExists(AccountAlreadyExistsException ex){
        log.error(HttpStatus.CONFLICT + ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage()));
    }

    @ExceptionHandler(value= OperationTypeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOperationTypeNotFoundException( OperationTypeNotFoundException ex){
        log.error(HttpStatus.NOT_FOUND + ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(value= TransactionInvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleTransactionInvalidRequestException(TransactionInvalidRequestException ex){
        log.error(HttpStatus.BAD_REQUEST + ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }
}
