package com.fmt.educonnect.controllers.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestLoginDTO(
        @NotBlank() String login,
        @NotBlank() String password) {


}
