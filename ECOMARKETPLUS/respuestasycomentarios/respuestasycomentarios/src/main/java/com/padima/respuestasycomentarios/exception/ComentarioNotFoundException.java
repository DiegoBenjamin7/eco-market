package com.padima.respuestasycomentarios.exception;

public class ComentarioNotFoundException extends RuntimeException {
    public ComentarioNotFoundException(String message) {
        super(message);
    }
}
