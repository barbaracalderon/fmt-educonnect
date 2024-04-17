package com.fmt.educonnect.infra.exceptions;

public class CursoNotFoundException extends RuntimeException {

    public CursoNotFoundException(String message) {
        super(message);
    }
}
