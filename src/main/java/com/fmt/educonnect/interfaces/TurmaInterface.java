package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestTurmaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseTurmaDTO;
import com.fmt.educonnect.datasource.entities.TurmaEntity;

import java.util.List;

public interface TurmaInterface {
    ResponseTurmaDTO criarTurma(RequestTurmaDTO requestTurmaDTO);

    TurmaEntity converterParaEntidade(RequestTurmaDTO requestTurmaDTO);

    ResponseTurmaDTO converterParaResponseDTO(TurmaEntity turmaEntitySalvo);

    List<ResponseTurmaDTO> listarTurmas();

    List<ResponseTurmaDTO> converterParaListaDeResponseDTO(List<TurmaEntity> turmaEntityList);

    ResponseTurmaDTO buscarTurmaPorId(Long id);

    ResponseTurmaDTO atualizarTurma(Long id, RequestTurmaDTO requestTurmaDTO);

    Void deletarTurma(Long id);
}
