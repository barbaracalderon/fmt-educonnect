package com.fmt.educonnect.controllers.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestCadastroDTO(
        @NotBlank() String nome,
        @NotBlank() String login,
        @NotBlank() @Size(min = 4) String password,
        @NotNull() Long idPapel) {
}
