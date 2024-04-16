package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestMateriaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseMateriaDTO;
import com.fmt.educonnect.datasource.entities.MateriaEntity;

import java.util.List;

public interface MateriaInterface {

    MateriaEntity criarMateria(RequestMateriaDTO requestMateriaDTO);

    MateriaEntity criarMateriaEntity(RequestMateriaDTO requestMateriaDTO);

    ResponseMateriaDTO criarResponseMateriaDTO(MateriaEntity materiaEntitySalvo);

    List<ResponseMateriaDTO> criarResponseMateriaDTO(List<MateriaEntity> materiaEntityList);

    List<MateriaEntity> listarMaterias();

    MateriaEntity buscarMateriaPorId(Long id);

    MateriaEntity atualizarMateria(Long id, RequestMateriaDTO requestMateriaDTO);

    Void deletarMateria(Long id);

    List<MateriaEntity> listarMateriasPorCurso(Long idCurso);
}
