package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestMateriaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseMateriaDTO;
import com.fmt.educonnect.datasource.entities.MateriaEntity;
import com.fmt.educonnect.datasource.repositories.CursoRepository;
import com.fmt.educonnect.datasource.repositories.MateriaRepository;

import java.util.List;

public interface MateriaInterface {

    ResponseMateriaDTO criarMateria(RequestMateriaDTO requestMateriaDTO);

    MateriaEntity converterParaEntidade(RequestMateriaDTO requestMateriaDTO);

    ResponseMateriaDTO converterParaResponseDTO(MateriaEntity materiaEntitySalvo);

    List<ResponseMateriaDTO> listarMaterias();

    List<ResponseMateriaDTO> converterParaListaDeResponseDTO(List<MateriaEntity> materiaEntityList);

    ResponseMateriaDTO buscarMateriaPorId(Long id);

    ResponseMateriaDTO atualizarMateria(Long id, RequestMateriaDTO requestMateriaDTO);

    Void deletarMateria(Long id);

    List<ResponseMateriaDTO> listarMateriasPorCurso(Long idCurso);
}
