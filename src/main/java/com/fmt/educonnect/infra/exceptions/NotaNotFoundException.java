package com.fmt.educonnect.infra.exceptions;

public class NotaNotFoundException extends RuntimeException {

    public NotaNotFoundException(String message) {
        super(message);
    }
}
