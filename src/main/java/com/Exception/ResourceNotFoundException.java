package com.Exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6015397247897225412L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
