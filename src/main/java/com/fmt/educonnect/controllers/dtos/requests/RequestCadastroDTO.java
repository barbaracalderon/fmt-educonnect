package com.fmt.educonnect.controllers.dtos.requests;

import com.fmt.educonnect.datasource.entities.UserRoleEntity;

public record RequestCadastroDTO(String login, String password, UserRoleEntity role){
}

