package com.fmt.educonnect.controllers.dtos.requests;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record RequestTurmaDTO (
        @NotBlank() String nome,
        @NotNull() @Past() @JsonFormat(pattern = "dd-MM-yyyy") LocalDate dataEntrada,
        @NotNull() Long idDocente,
        @NotNull() Long idCurso) {
}
