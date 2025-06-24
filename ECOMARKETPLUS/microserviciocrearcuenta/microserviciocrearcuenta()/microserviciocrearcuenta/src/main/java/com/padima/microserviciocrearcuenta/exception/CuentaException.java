package com.padima.microserviciocrearcuenta.exception;

public class CuentaException extends RuntimeException {
    public CuentaException(String message) {
        super(message);
    }
}