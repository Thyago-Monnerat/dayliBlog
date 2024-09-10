package com.dayliBlog.exceptions;

public class DifferentPasswordsException  extends RuntimeException{
    public DifferentPasswordsException(String message){
        super(message);
    }
}
