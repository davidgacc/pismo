package com.pismo.exceptions;

public class OperationTypeNotFoundException extends RuntimeException {

    public OperationTypeNotFoundException(String message) {
        super(message);
    }
}
