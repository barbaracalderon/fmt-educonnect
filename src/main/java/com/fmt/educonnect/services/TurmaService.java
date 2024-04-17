package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.requests.RequestTurmaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseTurmaDTO;
import com.fmt.educonnect.datasource.entities.AlunoEntity;
import com.fmt.educonnect.datasource.entities.TurmaEntity;
import com.fmt.educonnect.datasource.entities.CursoEntity;
import com.fmt.educonnect.datasource.entities.DocenteEntity;
import com.fmt.educonnect.datasource.repositories.TurmaRepository;
import com.fmt.educonnect.infra.exceptions.CursoNotFoundException;
import com.fmt.educonnect.infra.exceptions.DocenteNotFoundException;
import com.fmt.educonnect.infra.exceptions.TurmaNotFoundException;
import com.fmt.educonnect.interfaces.TurmaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurmaService implements TurmaInterface {


    private final TurmaRepository turmaRepository;
    private final AlunoService alunoService;
    private final CursoService cursoService;
    private final DocenteService docenteService;

    @Autowired
    public TurmaService(TurmaRepository turmaRepository,
                        AlunoService alunoService,
                        CursoService cursoService,
                        DocenteService docenteService
    ) {
        this.turmaRepository = turmaRepository;
        this.alunoService = alunoService;
        this.cursoService = cursoService;
        this.docenteService = docenteService;
    }

    @Override
    public TurmaEntity criarTurma(RequestTurmaDTO requestTurmaDTO) {

        CursoEntity cursoEntity = cursoService.buscarCursoPorId(requestTurmaDTO.idCurso());
        DocenteEntity docenteEntity = docenteService.buscarDocentePorId(requestTurmaDTO.idDocente());
        TurmaEntity turmaEntity = criarTurmaEntity(requestTurmaDTO);
        return turmaRepository.save(turmaEntity);
    }

    @Override
    public TurmaEntity criarTurmaEntity(RequestTurmaDTO requestTurmaDTO){
        TurmaEntity turmaEntity = new TurmaEntity();

        turmaEntity.setNome(requestTurmaDTO.nome());
        turmaEntity.setDataEntrada(requestTurmaDTO.dataEntrada());
        turmaEntity.setIdDocente(requestTurmaDTO.idDocente());
        turmaEntity.setIdCurso(requestTurmaDTO.idCurso());

        return turmaEntity;
    }

    @Override
    public ResponseTurmaDTO criarResponseTurmaDTO(TurmaEntity turmaEntitySalvo) {
        List<AlunoEntity> alunoEntityList = alunoService.buscarAlunosDeIdTurma(turmaEntitySalvo.getId());
        return new ResponseTurmaDTO(
                turmaEntitySalvo.getId(),
                turmaEntitySalvo.getNome(),
                turmaEntitySalvo.getDataEntrada(),
                turmaEntitySalvo.getIdDocente(),
                turmaEntitySalvo.getIdCurso(),
                alunoEntityList
        );
    }


    @Override
    public List<TurmaEntity> listarTurmas() {
        return turmaRepository.findAll();
    }

    @Override
    public List<ResponseTurmaDTO> criarResponseTurmaDTO(List<TurmaEntity> turmaEntityList) {
        return turmaEntityList.stream()
                .map(this::criarResponseTurmaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TurmaEntity buscarTurmaPorId(Long id) {
        return turmaRepository.findById(id)
                .orElseThrow(() -> new TurmaNotFoundException("Id da Turma não encontrado: " + id));
    }

    @Override
    public TurmaEntity atualizarTurma(Long id, RequestTurmaDTO requestTurmaDTO) {
        return turmaRepository.findById(id)
                .map(turma -> {
                    turma.setNome(requestTurmaDTO.nome());
                    turma.setDataEntrada(requestTurmaDTO.dataEntrada());
                    turma.setIdDocente(requestTurmaDTO.idDocente());
                    turma.setIdCurso(requestTurmaDTO.idCurso());
                    return turma;
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
