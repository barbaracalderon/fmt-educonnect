package com.fmt.educonnect.controllers.dtos.responses;

import java.util.List;

public record ResponseAlunoListaDeNotasDTO(
        Long idAluno,

        List<Long> valores) {
}
