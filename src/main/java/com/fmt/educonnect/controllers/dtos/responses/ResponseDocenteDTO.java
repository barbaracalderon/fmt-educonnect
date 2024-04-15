package com.fmt.educonnect.controllers.dtos.responses;

import java.time.LocalDate;

public record ResponseDocenteDTO (
        Long id,
        String nome,
        LocalDate dataEntrada,
        Long idCadastro) {
}
