package com.team01.userservice.exception;

public class UserIdAndPasswordMismatchException extends Exception {

    public UserIdAndPasswordMismatchException(String message) {
        super(message);
    }
}