package com.dayliBlog.exceptions;

public class CannotDeletePostException extends RuntimeException{
    public CannotDeletePostException(String message) {
        super(message);
    }
}
