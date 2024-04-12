package com.fmt.educonnect.controllers.dtos.responses;

import com.fmt.educonnect.datasource.entities.UserRoleEntity;

public record ResponseCadastroDTO (String id, String login, UserRoleEntity role){
}
