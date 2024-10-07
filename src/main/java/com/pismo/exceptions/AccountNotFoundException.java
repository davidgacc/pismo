package com.pismo.exceptions;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
    }
    public AccountNotFoundException(String message) {
        super(message);
    }
}
