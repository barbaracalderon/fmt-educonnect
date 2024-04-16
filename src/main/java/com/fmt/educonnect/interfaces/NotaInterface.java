package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestNotaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseNotaDTO;
import com.fmt.educonnect.datasource.entities.NotaEntity;

import java.util.List;

public interface NotaInterface {
    NotaEntity criarNota(RequestNotaDTO requestNotaDTO);

    NotaEntity criarNotaEntity(RequestNotaDTO requestNotaDTO);

    ResponseNotaDTO criarResponseNotaDTO(NotaEntity notaEntitySalvo);

    List<ResponseNotaDTO> criarResponseNotaDTO(List<NotaEntity> notaEntityList);

    List<NotaEntity> listarNotas();

    NotaEntity buscarNotaPorId(Long id);

    NotaEntity atualizarNota(Long id, RequestNotaDTO requestNotaDTO);

    Void deletarNota(Long id);

    List<NotaEntity> buscarNotasPorIdAluno(Long idAluno);
}
