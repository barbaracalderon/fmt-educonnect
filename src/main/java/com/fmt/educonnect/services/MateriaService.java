package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.requests.RequestMateriaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseMateriaDTO;
import com.fmt.educonnect.datasource.entities.CursoEntity;
import com.fmt.educonnect.datasource.entities.MateriaEntity;
import com.fmt.educonnect.datasource.repositories.CursoRepository;
import com.fmt.educonnect.datasource.repositories.MateriaRepository;
import com.fmt.educonnect.infra.exceptions.CursoNotFoundException;
import com.fmt.educonnect.infra.exceptions.MateriaNotFoundException;
import com.fmt.educonnect.interfaces.MateriaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MateriaService implements MateriaInterface {


    private final MateriaRepository materiaRepository;
    private final CursoRepository cursoRepository;

    @Autowired
    public MateriaService(MateriaRepository materiaRepository, CursoRepository cursoRepository) {
        this.materiaRepository = materiaRepository;
        this.cursoRepository = cursoRepository;
    }

    @Override
    public ResponseMateriaDTO criarMateria(RequestMateriaDTO requestMateriaDTO) {

        Optional<CursoEntity> optionalCursoEntity = cursoRepository.findById(requestMateriaDTO.idCurso());
        CursoEntity cursoEntity = optionalCursoEntity.orElseThrow(
                () -> new CursoNotFoundException("Id do Curso inválido: " + requestMateriaDTO.idCurso())
        );

        MateriaEntity materiaEntity = converterParaEntidade(requestMateriaDTO);
        MateriaEntity materiaEntitySalvo = materiaRepository.save(materiaEntity);
        return converterParaResponseDTO(materiaEntitySalvo);
    }

    @Override
    public MateriaEntity converterParaEntidade(RequestMateriaDTO requestMateriaDTO){
        MateriaEntity materiaEntity = new MateriaEntity();

        materiaEntity.setNome(requestMateriaDTO.nome());
        materiaEntity.setDataEntrada(requestMateriaDTO.dataEntrada());
        materiaEntity.setIdCurso(requestMateriaDTO.idCurso());

        return materiaEntity;
    }

    @Override
    public ResponseMateriaDTO converterParaResponseDTO(MateriaEntity materiaEntitySalvo){
        return new ResponseMateriaDTO(
                materiaEntitySalvo.getId(),
                materiaEntitySalvo.getNome(),
                materiaEntitySalvo.getDataEntrada(),
                materiaEntitySalvo.getIdCurso()
        );
    }


    @Override
    public List<ResponseMateriaDTO> listarMaterias() {
        List<MateriaEntity> materiaEntityList = materiaRepository.findAll();
        return converterParaListaDeResponseDTO(materiaEntityList);
    }

    @Override
    public List<ResponseMateriaDTO> converterParaListaDeResponseDTO(List<MateriaEntity> materiaEntityList) {
        return materiaEntityList.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseMateriaDTO buscarMateriaPorId(Long id) {
        return materiaRepository.findById(id)
                .map(this::converterParaResponseDTO)
                .orElseThrow(() -> new MateriaNotFoundException("Id da Materia não encontrado: " + id));
    }

    @Override
    public ResponseMateriaDTO atualizarMateria(Long id, RequestMateriaDTO requestMateriaDTO) {
        return materiaRepository.findById(id)
                .map(materia -> {
                    materia.setNome(requestMateriaDTO.nome());
                    materia.setDataEntrada(requestMateriaDTO.dataEntrada());
                    MateriaEntity updatedMateria = materiaRepository.save(materia);
                    return converterParaResponseDTO(updatedMateria);
                })
                .orElseThrow(() -> new MateriaNotFoundException("Id da Materia não encontrado para atualizar: " + id));
    }

    @Override
    public Void deletarMateria(Long id) {
        materiaRepository.findById(id)
                .orElseThrow(() -> new MateriaNotFoundException("Id da Materia não encontrado para deletar: " + id));
        materiaRepository.deleteById(id);
        return null;
    }



}
