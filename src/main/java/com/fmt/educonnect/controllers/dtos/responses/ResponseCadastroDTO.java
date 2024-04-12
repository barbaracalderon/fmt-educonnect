package com.fmt.educonnect.controllers.dtos.responses;

import com.fmt.educonnect.datasource.entities.UserRoleEnum;

public record ResponseCadastroDTO (Integer id, String login, UserRoleEnum role){
}
