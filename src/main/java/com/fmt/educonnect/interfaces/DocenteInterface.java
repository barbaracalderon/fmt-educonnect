package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestDocenteDTO;
import com.fmt.educonnect.datasource.entities.DocenteEntity;

import java.util.List;

public interface DocenteInterface {

    RequestDocenteDTO criarDocente(RequestDocenteDTO requestDocenteDTO);
    DocenteEntity converterParaEntidade(RequestDocenteDTO requestDocenteDTO);
    RequestDocenteDTO converterParaResponseDTO(DocenteEntity docenteEntity);
    List<RequestDocenteDTO> listarDocentes();
    List<RequestDocenteDTO> converterParaListaDeResponseDTO(List<DocenteEntity> docentes);
}
