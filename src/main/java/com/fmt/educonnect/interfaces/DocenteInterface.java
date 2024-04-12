package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestDocenteDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseDocenteDTO;
import com.fmt.educonnect.datasource.entities.DocenteEntity;

import java.util.List;

public interface DocenteInterface {

    ResponseDocenteDTO criarDocente(RequestDocenteDTO requestDocenteDTO);

    DocenteEntity converterParaEntidade(RequestDocenteDTO requestDocenteDTO);

    ResponseDocenteDTO converterParaResponseDTO(DocenteEntity docenteEntity);

    List<ResponseDocenteDTO> listarDocentes();

    List<ResponseDocenteDTO> converterParaListaDeResponseDTO(List<DocenteEntity> docentes);
}
