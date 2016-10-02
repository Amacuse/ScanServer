package com.Exception;

public class ConstraintViolationException extends RuntimeException {

    private static final long serialVersionUID = -7135112322562445316L;

    public ConstraintViolationException(String message) {
        super(message);
    }
}
