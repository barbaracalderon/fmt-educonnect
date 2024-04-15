package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.requests.RequestTurmaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseTurmaDTO;
import com.fmt.educonnect.datasource.entities.AlunoEntity;
import com.fmt.educonnect.datasource.entities.CursoEntity;
import com.fmt.educonnect.datasource.entities.DocenteEntity;
import com.fmt.educonnect.datasource.entities.TurmaEntity;
import com.fmt.educonnect.datasource.repositories.AlunoRepository;
import com.fmt.educonnect.datasource.repositories.CursoRepository;
import com.fmt.educonnect.datasource.repositories.DocenteRepository;
import com.fmt.educonnect.datasource.repositories.TurmaRepository;
import com.fmt.educonnect.infra.exceptions.CursoNotFoundException;
import com.fmt.educonnect.infra.exceptions.DocenteNotFoundException;
import com.fmt.educonnect.infra.exceptions.TurmaNotFoundException;
import com.fmt.educonnect.interfaces.TurmaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurmaService implements TurmaInterface {


    private final TurmaRepository turmaRepository;
    private final CursoRepository cursoRepository;
    private final DocenteRepository docenteRepository;
    private final AlunoRepository alunoRepository;

    @Autowired
    public TurmaService(TurmaRepository turmaRepository,
                        CursoRepository cursoRepository,
                        DocenteRepository docenteRepository,
                        AlunoRepository alunoRepository
    ) {
        this.turmaRepository = turmaRepository;
        this.cursoRepository = cursoRepository;
        this.docenteRepository = docenteRepository;
        this.alunoRepository = alunoRepository;
    }

    @Override
    public ResponseTurmaDTO criarTurma(RequestTurmaDTO requestTurmaDTO) {

        Optional<CursoEntity> optionalCursoEntity = cursoRepository.findById(requestTurmaDTO.idCurso());
        CursoEntity cursoEntity = optionalCursoEntity.orElseThrow(
                () -> new CursoNotFoundException("Id do Curso inválido: " + requestTurmaDTO.idCurso())
        );

        Optional<DocenteEntity> optionalDocenteEntity = docenteRepository.findById(requestTurmaDTO.idDocente());
        DocenteEntity docenteEntity = optionalDocenteEntity.orElseThrow(
                () -> new DocenteNotFoundException("Id do Docente inválido: " + requestTurmaDTO.idDocente())
        );

        TurmaEntity turmaEntity = converterParaEntidade(requestTurmaDTO);
        TurmaEntity turmaEntitySalvo = turmaRepository.save(turmaEntity);
        return converterParaResponseDTO(turmaEntitySalvo);
    }

    @Override
    public TurmaEntity converterParaEntidade(RequestTurmaDTO requestTurmaDTO){
        TurmaEntity turmaEntity = new TurmaEntity();

        turmaEntity.setNome(requestTurmaDTO.nome());
        turmaEntity.setDataEntrada(requestTurmaDTO.dataEntrada());
        turmaEntity.setIdDocente(requestTurmaDTO.idDocente());
        turmaEntity.setIdCurso(requestTurmaDTO.idCurso());

        return turmaEntity;
    }

    @Override
    public ResponseTurmaDTO converterParaResponseDTO(TurmaEntity turmaEntitySalvo){

        List<AlunoEntity> optionalAlunoEntityList = alunoRepository.findAllByIdTurma(turmaEntitySalvo.getId());


        return new ResponseTurmaDTO(
                turmaEntitySalvo.getId(),
                turmaEntitySalvo.getNome(),
                turmaEntitySalvo.getDataEntrada(),
                turmaEntitySalvo.getIdDocente(),
                turmaEntitySalvo.getIdCurso(),
                optionalAlunoEntityList
        );
    }


    @Override
    public List<ResponseTurmaDTO> listarTurmas() {
        List<TurmaEntity> turmaEntityList = turmaRepository.findAll();
        return converterParaListaDeResponseDTO(turmaEntityList);
    }

    @Override
    public List<ResponseTurmaDTO> converterParaListaDeResponseDTO(List<TurmaEntity> turmaEntityList) {
        return turmaEntityList.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseTurmaDTO buscarTurmaPorId(Long id) {
        return turmaRepository.findById(id)
                .map(this::converterParaResponseDTO)
                .orElseThrow(() -> new TurmaNotFoundException("Id da Turma não encontrado: " + id));
    }

    @Override
    public ResponseTurmaDTO atualizarTurma(Long id, RequestTurmaDTO requestTurmaDTO) {
        return turmaRepository.findById(id)
                .map(turma -> {
                    turma.setNome(requestTurmaDTO.nome());
                    turma.setDataEntrada(requestTurmaDTO.dataEntrada());
                    TurmaEntity updatedTurma = turmaRepository.save(turma);
                    return converterParaResponseDTO(updatedTurma);
                })
                .orElseThrow(() -> new TurmaNotFoundException("Id da Turma não encontrado para atualizar: " + id));
    }

    @Override
    public Void deletarTurma(Long id) {
        turmaRepository.findById(id)
                .orElseThrow(() -> new TurmaNotFoundException("Id da Turma não encontrado para deletar: " + id));
        turmaRepository.deleteById(id);
        return null;
    }


}
