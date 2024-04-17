package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.requests.RequestCursoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCursoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCursoMateriasDTO;
import com.fmt.educonnect.datasource.entities.CursoEntity;
import com.fmt.educonnect.datasource.entities.MateriaEntity;
import com.fmt.educonnect.datasource.repositories.CursoRepository;
import com.fmt.educonnect.datasource.repositories.MateriaRepository;
import com.fmt.educonnect.infra.exceptions.CursoNotFoundException;
import com.fmt.educonnect.infra.exceptions.MateriaNotFoundException;
import com.fmt.educonnect.interfaces.CursoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService implements CursoInterface {

    private final CursoRepository cursoRepository;

    @Autowired
    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public CursoEntity criarCurso(RequestCursoDTO requestCursoDTO) {
        CursoEntity cursoEntity = criarCursoEntity(requestCursoDTO);
        return cursoRepository.save(cursoEntity);
    }

    @Override
    public CursoEntity criarCursoEntity(RequestCursoDTO requestCursoDTO){
        CursoEntity cursoEntity = new CursoEntity();

        cursoEntity.setNome(requestCursoDTO.nome());
        cursoEntity.setDataEntrada(requestCursoDTO.dataEntrada());

        return cursoEntity;
    }

    @Override
    public ResponseCursoDTO criarResponseCursoDTO(CursoEntity cursoEntitySalvo){
        return new ResponseCursoDTO(
                cursoEntitySalvo.getId(),
                cursoEntitySalvo.getNome(),
                cursoEntitySalvo.getDataEntrada()
        );
    }

    @Override
    public List<CursoEntity> listarCursos() {
        return cursoRepository.findAll();
    }

    @Override
    public List<ResponseCursoDTO> criarResponseCursoDTO(List<CursoEntity> cursoEntityList) {
        return cursoEntityList.stream()
                .map(this::criarResponseCursoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CursoEntity buscarCursoPorId(Long id) {
        return cursoRepository.findById(id).orElseThrow(
                () -> new CursoNotFoundException("Id do Curso não encontrado: " + id));
    }

    @Override
    public CursoEntity atualizarCurso(Long id, RequestCursoDTO requestCursoDTO) {
        return cursoRepository.findById(id)
                .map(curso -> {
                    curso.setNome(requestCursoDTO.nome());
                    curso.setDataEntrada(requestCursoDTO.dataEntrada());
                    return cursoRepository.save(curso);
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


}
