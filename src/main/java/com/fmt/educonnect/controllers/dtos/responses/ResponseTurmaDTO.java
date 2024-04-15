package com.fmt.educonnect.controllers.dtos.responses;

import java.time.LocalDate;
import java.util.List;

public record ResponseTurmaDTO (
    Long id,
    String nome,
    LocalDate dataEntrada,
    List alunos
    ){
}
