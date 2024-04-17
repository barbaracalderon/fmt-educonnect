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
    private final CursoService cursoService;

    @Autowired
    public MateriaService(MateriaRepository materiaRepository, CursoService cursoService) {
        this.materiaRepository = materiaRepository;
        this.cursoService = cursoService;
    }

    @Override
    public MateriaEntity criarMateria(RequestMateriaDTO requestMateriaDTO) {

        CursoEntity cursoEntity = cursoService.buscarCursoPorId(requestMateriaDTO.idCurso());
        MateriaEntity materiaEntity = criarMateriaEntity(requestMateriaDTO);
        return materiaRepository.save(materiaEntity);
    }

    public List<MateriaEntity> buscarMateriaPorIdCurso(Long idCurso) {
        return materiaRepository.findAllByIdCurso(idCurso);
    }

    @Override
    public MateriaEntity criarMateriaEntity(RequestMateriaDTO requestMateriaDTO){
        MateriaEntity materiaEntity = new MateriaEntity();

        materiaEntity.setNome(requestMateriaDTO.nome());
        materiaEntity.setDataEntrada(requestMateriaDTO.dataEntrada());
        materiaEntity.setIdCurso(requestMateriaDTO.idCurso());

        return materiaEntity;
    }

    @Override
    public ResponseMateriaDTO criarResponseMateriaDTO(MateriaEntity materiaEntitySalvo){
        return new ResponseMateriaDTO(
                materiaEntitySalvo.getId(),
                materiaEntitySalvo.getNome(),
                materiaEntitySalvo.getDataEntrada(),
                materiaEntitySalvo.getIdCurso()
        );
    }

    @Override
    public List<MateriaEntity> listarMaterias() {
        return materiaRepository.findAll();
    }

    @Override
    public List<ResponseMateriaDTO> criarResponseMateriaDTO(List<MateriaEntity> materiaEntityList) {
        return materiaEntityList.stream()
                .map(this::criarResponseMateriaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MateriaEntity buscarMateriaPorId(Long id) {
        return materiaRepository.findById(id).orElseThrow(
                () -> new MateriaNotFoundException("Id da Materia não encontrada: " + id));
    }


    @Override
    public MateriaEntity atualizarMateria(Long id, RequestMateriaDTO requestMateriaDTO) {
        return materiaRepository.findById(id)
                .map(materia -> {
                    materia.setNome(requestMateriaDTO.nome());
                    materia.setDataEntrada(requestMateriaDTO.dataEntrada());
                    return materiaRepository.save(materia);
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

    @Override
    public List<MateriaEntity> listarMateriasPorCurso(Long idCurso) {
        return materiaRepository.findAllByIdCurso(idCurso);
    }

    public List<MateriaEntity> buscarCursoPorId(Long idCurso) {
        return materiaRepository.findAllByIdCurso(idCurso);
    }


}
