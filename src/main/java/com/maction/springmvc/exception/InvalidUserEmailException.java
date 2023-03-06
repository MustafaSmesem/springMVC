package com.maction.springmvc.exception;

public class InvalidUserEmailException extends IllegalArgumentException {
    public InvalidUserEmailException(String message) {
        super(message);
    }
}
