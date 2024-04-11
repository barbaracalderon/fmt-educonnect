package com.fmt.educonnect.interfaces.dtos;

import com.fmt.educonnect.models.UserRole;

public record RegisterDTO (String login, String password, UserRole role){
}

