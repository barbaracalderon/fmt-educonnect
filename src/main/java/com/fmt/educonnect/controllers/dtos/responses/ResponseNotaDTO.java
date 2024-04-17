package com.fmt.educonnect.controllers.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ResponseNotaDTO (
        Long id,
        @JsonFormat(pattern = "dd-MM-yyyy") LocalDate dataLancamento,
        Long IdAluno,
        Long IdDocente,
        Long IdMateria,
        Long valor) {
}
