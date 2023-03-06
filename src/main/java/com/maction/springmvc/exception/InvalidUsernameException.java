package com.maction.springmvc.exception;

public class InvalidUsernameException extends IllegalArgumentException {
    public InvalidUsernameException(String message) {
        super(message);
    }
}
