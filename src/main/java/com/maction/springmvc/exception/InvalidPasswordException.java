package com.maction.springmvc.exception;

public class InvalidPasswordException extends IllegalArgumentException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
