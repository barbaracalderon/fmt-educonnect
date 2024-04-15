package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.requests.RequestCursoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCursoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCursoMateriasDTO;
import com.fmt.educonnect.datasource.entities.CursoEntity;
import com.fmt.educonnect.datasource.entities.MateriaEntity;
import com.fmt.educonnect.datasource.repositories.CursoRepository;
import com.fmt.educonnect.datasource.repositories.MateriaRepository;
import com.fmt.educonnect.infra.exceptions.CursoNotFoundException;
import com.fmt.educonnect.interfaces.CursoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService implements CursoInterface {


    private final CursoRepository cursoRepository;
    private final MateriaRepository materiaRepository;

    @Autowired
    public CursoService(CursoRepository cursoRepository, MateriaRepository materiaRepository) {
        this.cursoRepository = cursoRepository;
        this.materiaRepository = materiaRepository;
    }

    @Override
    public ResponseCursoDTO criarCurso(RequestCursoDTO requestCursoDTO) {
        CursoEntity cursoEntity = converterParaEntidade(requestCursoDTO);
        CursoEntity cursoEntitySalvo = cursoRepository.save(cursoEntity);
        return converterParaResponseDTO(cursoEntitySalvo);
    }

    @Override
    public CursoEntity converterParaEntidade(RequestCursoDTO requestCursoDTO){
        CursoEntity cursoEntity = new CursoEntity();

        cursoEntity.setNome(requestCursoDTO.nome());
        cursoEntity.setDataEntrada(requestCursoDTO.dataEntrada());

        return cursoEntity;
    }

    @Override
    public ResponseCursoDTO converterParaResponseDTO(CursoEntity cursoEntitySalvo){
        return new ResponseCursoDTO(
                cursoEntitySalvo.getId(),
                cursoEntitySalvo.getNome(),
                cursoEntitySalvo.getDataEntrada()
        );
    }


    @Override
    public List<ResponseCursoDTO> listarCursos() {
        List<CursoEntity> cursoEntityList = cursoRepository.findAll();
        return converterParaListaDeResponseDTO(cursoEntityList);
    }

    @Override
    public List<ResponseCursoDTO> converterParaListaDeResponseDTO(List<CursoEntity> cursoEntityList) {
        return cursoEntityList.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseCursoDTO buscarCursoPorId(Long id) {
        return cursoRepository.findById(id)
                .map(this::converterParaResponseDTO)
                .orElseThrow(() -> new CursoNotFoundException("Id do Curso não encontrado: " + id));
    }

    @Override
    public ResponseCursoDTO atualizarCurso(Long id, RequestCursoDTO requestCursoDTO) {
        return cursoRepository.findById(id)
                .map(curso -> {
                    curso.setNome(requestCursoDTO.nome());
                    curso.setDataEntrada(requestCursoDTO.dataEntrada());
                    CursoEntity updatedCurso = cursoRepository.save(curso);
                    return converterParaResponseDTO(updatedCurso);
                })
                .orElseThrow(() -> new CursoNotFoundException("Id do Curso não encontrado para atualizar: " + id));
    }

    @Override
    public Void deletarCurso(Long id) {
        cursoRepository.findById(id)
                .orElseThrow(() -> new CursoNotFoundException("Id do Curso não encontrado para deletar: " + id));
        cursoRepository.deleteById(id);
        return null;
    }

    public List<ResponseCursoMateriasDTO> buscarMateriasDeCursoId(Long idCurso) {
        List<MateriaEntity> materiaEntityList = materiaRepository.findAllByIdCurso(idCurso);
        if (materiaEntityList.isEmpty()) {
            throw new CursoNotFoundException("Id do Curso não encontrado: " + idCurso);
        } else {
            return Collections.singletonList(new ResponseCursoMateriasDTO(
                    idCurso,
                    materiaEntityList
            ));
        }
    }



}
