package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestDocenteDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseDocenteDTO;
import com.fmt.educonnect.datasource.entities.DocenteEntity;

import java.util.List;

public interface DocenteInterface {

    DocenteEntity criarDocente(RequestDocenteDTO requestDocenteDTO);

    DocenteEntity criarDocenteEntity(RequestDocenteDTO requestDocenteDTO);

    ResponseDocenteDTO criarResponseDocenteDTO(DocenteEntity docenteEntitySalvo);

    List<ResponseDocenteDTO> criarResponseDocenteDTO(List<DocenteEntity> docenteEntityList);

    List<DocenteEntity> listarDocentes();

    DocenteEntity buscarDocentePorId(Long id);

    DocenteEntity atualizarDocente(Long id, RequestDocenteDTO body);

    Void deletarDocente(Long id);
}
