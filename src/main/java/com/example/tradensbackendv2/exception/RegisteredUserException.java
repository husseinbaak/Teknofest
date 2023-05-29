package com.example.tradensbackendv2.exception;

public class RegisteredUserException extends RuntimeException{
    public RegisteredUserException(String s){
        super(s);
    }
}
