package com.fmt.educonnect.controllers.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RequestNotaDTO (
        @NotNull() @Past @JsonFormat(pattern = "dd-MM-yyyy") LocalDate dataLancamento,
        @NotNull() Long idAluno,
        @NotNull() Long idDocente,
        @NotNull() Long idMateria,
        @NotNull() @PositiveOrZero() @Max(value = 100) Long valor) {
}
