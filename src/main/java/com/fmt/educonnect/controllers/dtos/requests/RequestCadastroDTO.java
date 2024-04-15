package com.fmt.educonnect.controllers.dtos.requests;

public record RequestCadastroDTO(
        String nome,
        String login,
        String password,
        Long idPapel) {
}
