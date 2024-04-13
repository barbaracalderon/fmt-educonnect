package com.fmt.educonnect.controllers.dtos.responses;

import java.time.LocalDate;

public record ResponseAlunoDTO (
        Integer id,
        String nome,
        LocalDate dataNascimento,
        int idCadastro
    ) {

}
