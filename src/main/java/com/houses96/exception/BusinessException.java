package com.houses96.exception;

public class BusinessException extends Exception {

    // Default constructor
    public BusinessException() {
        super();
    }

    // Constructor that accepts a custom message
    public BusinessException(String message) {
        super(message);
    }

    // Constructor that accepts a custom message and a cause
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that accepts a cause
    public BusinessException(Throwable cause) {
        super(cause);
    }
}

