package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestCursoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCursoDTO;
import com.fmt.educonnect.datasource.entities.CursoEntity;

import java.util.List;

public interface CursoInterface {

    CursoEntity criarCurso(RequestCursoDTO requestCursoDTO);

    CursoEntity criarCursoEntity(RequestCursoDTO requestCursoDTO);

    ResponseCursoDTO criarResponseCursoDTO(CursoEntity cursoEntitySalvo);

    List<ResponseCursoDTO> criarResponseCursoDTO(List<CursoEntity> cursoEntityList);

    List<CursoEntity> listarCursos();

    CursoEntity buscarCursoPorId(Long id);

    CursoEntity atualizarCurso(Long id, RequestCursoDTO body);

    Void deletarCurso(Long id);
}
