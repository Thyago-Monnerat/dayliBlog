package com.dayliBlog.exceptions;

public class CannotFindUserAccountException extends RuntimeException {
    public CannotFindUserAccountException(String message) {
        super(message);
    }
}
