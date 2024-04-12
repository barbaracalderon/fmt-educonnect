package com.fmt.educonnect.controllers.dtos;

import com.fmt.educonnect.datasource.entities.UserRoleEntity;

public record RegisterDTO (String login, String password, UserRoleEntity role){
}

