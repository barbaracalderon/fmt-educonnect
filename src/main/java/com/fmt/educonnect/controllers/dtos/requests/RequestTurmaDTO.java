package com.fmt.educonnect.controllers.dtos.requests;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record RequestTurmaDTO (
        String nome,
        @JsonFormat(pattern = "dd-MM-yyyy") LocalDate dataEntrada,
        Long idDocente
){
}
