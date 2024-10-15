package com.pismo.exceptions;

public class AccountCreditLimitException extends RuntimeException {
    public AccountCreditLimitException(String message) {
        super(message);
    }
}
