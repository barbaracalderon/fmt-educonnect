package com.fmt.educonnect.controllers.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record RequestNotaDTO (
        @JsonFormat(pattern = "dd-MM-yyyy") LocalDate dataLancamento,
        Long IdAluno,
        Long IdDocente,
        Long IdMateria,
        Long valor
)
{
}
