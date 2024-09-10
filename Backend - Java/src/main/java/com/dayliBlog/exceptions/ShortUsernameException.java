package com.dayliBlog.exceptions;

public class ShortUsernameException extends RuntimeException{
    public ShortUsernameException(String message) {
        super(message);
    }
}
