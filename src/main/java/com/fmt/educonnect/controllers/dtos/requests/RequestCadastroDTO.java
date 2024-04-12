package com.fmt.educonnect.controllers.dtos.requests;

import com.fmt.educonnect.datasource.entities.UserRoleEnum;

public record RequestCadastroDTO(String login, String password, UserRoleEnum role) {
}

