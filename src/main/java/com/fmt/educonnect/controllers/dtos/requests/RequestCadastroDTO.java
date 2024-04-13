package com.fmt.educonnect.controllers.dtos.requests;

import com.fmt.educonnect.datasource.entities.UserRoleEnum;

public record RequestCadastroDTO(String nome, String login, String password, UserRoleEnum nomePapel) {
}
