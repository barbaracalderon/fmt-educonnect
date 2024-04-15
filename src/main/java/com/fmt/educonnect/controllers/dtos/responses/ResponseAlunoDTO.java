package com.fmt.educonnect.controllers.dtos.responses;

import java.time.LocalDate;

public record ResponseAlunoDTO (
        Long id,
        String nome,
        LocalDate dataNascimento,
        Long idCadastro,
        Long idTurma) {
}
