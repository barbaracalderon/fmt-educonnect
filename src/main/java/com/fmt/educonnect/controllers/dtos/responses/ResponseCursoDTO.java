package com.fmt.educonnect.controllers.dtos.responses;

import java.time.LocalDate;

public record ResponseCursoDTO (
        Long id,
        String nome,
        LocalDate dataEntrada) {
}
