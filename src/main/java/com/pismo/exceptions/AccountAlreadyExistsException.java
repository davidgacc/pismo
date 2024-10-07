package com.pismo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // 409 Conflict
public class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException(String documentNumber) {
        super("Account with document number " + documentNumber + " already exists.");
    }
}
