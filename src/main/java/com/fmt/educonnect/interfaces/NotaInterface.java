package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestNotaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseNotaDTO;
import com.fmt.educonnect.datasource.entities.NotaEntity;

import java.util.List;

public interface NotaInterface {
    ResponseNotaDTO criarNota(RequestNotaDTO requestNotaDTO);

    NotaEntity converterParaEntidade(RequestNotaDTO requestNotaDTO);

    ResponseNotaDTO converterParaResponseDTO(NotaEntity notaEntitySalvo);

    List<ResponseNotaDTO> listarNotas();

    List<ResponseNotaDTO> converterParaListaDeResponseDTO(List<NotaEntity> notaEntityList);

    ResponseNotaDTO buscarNotaPorId(Long id);

    ResponseNotaDTO atualizarNota(Long id, RequestNotaDTO requestNotaDTO);

    Void deletarNota(Long id);

    List<ResponseNotaDTO> buscarNotasDeAlunoId(Long id);
}
