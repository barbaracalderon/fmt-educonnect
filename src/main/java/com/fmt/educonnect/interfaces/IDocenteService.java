package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.interfaces.dtos.DocenteDTO;
import com.fmt.educonnect.models.DocenteModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IDocenteService {

    DocenteDTO criarDocente(DocenteDTO docenteDTO);

    DocenteModel converterParaEntidade(DocenteDTO docenteDTO);

    DocenteDTO converterParaResponseDTO(DocenteModel docenteModel);

    List<DocenteDTO> listarDocentes();

    List<DocenteDTO> converterParaListaDeResponseDTO(List<DocenteModel> docentes);
}
