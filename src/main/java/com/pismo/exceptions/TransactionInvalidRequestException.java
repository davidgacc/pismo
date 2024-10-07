package com.pismo.exceptions;

public class TransactionInvalidRequestException extends RuntimeException {

    public TransactionInvalidRequestException(String message) {
        super(message);
    }
}
