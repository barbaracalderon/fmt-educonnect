package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestTurmaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseTurmaDTO;
import com.fmt.educonnect.datasource.entities.TurmaEntity;

import java.util.List;

public interface TurmaInterface {
    TurmaEntity criarTurma(RequestTurmaDTO requestTurmaDTO);

    TurmaEntity criarTurmaEntity(RequestTurmaDTO requestTurmaDTO);

    ResponseTurmaDTO criarResponseTurmaDTO(TurmaEntity turmaEntitySalvo);

    List<ResponseTurmaDTO> criarResponseTurmaDTO(List<TurmaEntity> turmaEntityList);

    List<TurmaEntity> listarTurmas();

    TurmaEntity buscarTurmaPorId(Long id);

    TurmaEntity atualizarTurma(Long id, RequestTurmaDTO requestTurmaDTO);

    Void deletarTurma(Long id);
}
