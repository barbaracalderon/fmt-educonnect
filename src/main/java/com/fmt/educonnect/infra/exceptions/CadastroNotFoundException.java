package com.fmt.educonnect.infra.exceptions;

public class CadastroNotFoundException extends RuntimeException{

    public CadastroNotFoundException(String message) {
        super(message);
    }

}
