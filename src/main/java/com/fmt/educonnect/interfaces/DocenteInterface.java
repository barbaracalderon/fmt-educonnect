package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.DocenteDTO;
import com.fmt.educonnect.datasource.entities.DocenteEntity;

import java.util.List;

public interface DocenteInterface {

    DocenteDTO criarDocente(DocenteDTO docenteDTO);
    DocenteEntity converterParaEntidade(DocenteDTO docenteDTO);
    DocenteDTO converterParaResponseDTO(DocenteEntity docenteEntity);
    List<DocenteDTO> listarDocentes();
    List<DocenteDTO> converterParaListaDeResponseDTO(List<DocenteEntity> docentes);
}
