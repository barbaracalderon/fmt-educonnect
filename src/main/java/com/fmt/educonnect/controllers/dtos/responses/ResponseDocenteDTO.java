package com.fmt.educonnect.controllers.dtos.responses;

import java.time.LocalDate;

public record ResponseDocenteDTO (
        Integer id,
        String nome,
        LocalDate dataEntrada,
        int idCadastro
    ) {

}
