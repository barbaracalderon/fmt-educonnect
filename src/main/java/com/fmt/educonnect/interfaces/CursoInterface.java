package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestCursoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCursoDTO;
import com.fmt.educonnect.datasource.entities.CursoEntity;

import java.util.List;

public interface CursoInterface {

    ResponseCursoDTO criarCurso(RequestCursoDTO requestCursoDTO);

    CursoEntity converterParaEntidade(RequestCursoDTO requestCursoDTO);

    ResponseCursoDTO converterParaResponseDTO(CursoEntity docenteEntity);

    List<ResponseCursoDTO> listarCursos();

    List<ResponseCursoDTO> converterParaListaDeResponseDTO(List<CursoEntity> docentes);

    ResponseCursoDTO buscarCursoPorId(int id);

    ResponseCursoDTO atualizarCurso(int id, RequestCursoDTO body);

    Void deletarCurso(int id);
}
