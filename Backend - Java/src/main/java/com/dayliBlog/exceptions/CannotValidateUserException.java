package com.dayliBlog.exceptions;

public class CannotValidateUserException extends RuntimeException{
    public CannotValidateUserException(String message){
        super(message);
    }
}
