package com.dayliBlog.exceptions;

public class InvalidFirstCharacterException extends RuntimeException{
    public InvalidFirstCharacterException(String message) {
        super(message);
    }
}
