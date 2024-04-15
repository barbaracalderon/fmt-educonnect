package com.fmt.educonnect.controllers.dtos.responses;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record ResponseCadastroDTO(
        Long id,
        String nome,
        String login,
        Long idPapel) {
}
