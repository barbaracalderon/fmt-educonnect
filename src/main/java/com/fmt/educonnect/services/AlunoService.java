package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.requests.RequestAlunoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseAlunoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseAlunoListaDeNotasDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseAlunoPontuacaoDTO;
import com.fmt.educonnect.datasource.entities.AlunoEntity;
import com.fmt.educonnect.datasource.entities.NotaEntity;
import com.fmt.educonnect.datasource.repositories.AlunoRepository;
import com.fmt.educonnect.infra.exceptions.AlunoNotFoundException;

import com.fmt.educonnect.interfaces.AlunoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class AlunoService implements AlunoInterface {

    private final AlunoRepository alunoRepository;

    @Autowired
    public AlunoService(AlunoRepository alunoRepository
    ) {
        this.alunoRepository = alunoRepository;
    }


    @Override
    public AlunoEntity criarAluno(AlunoEntity alunoEntity) {
        return alunoRepository.save(alunoEntity);
    }

    @Override
    public AlunoEntity criarAlunoEntity(RequestAlunoDTO requestAlunoDTO) {
        AlunoEntity alunoEntity = new AlunoEntity();

        alunoEntity.setNome(requestAlunoDTO.nome());
        alunoEntity.setDataNascimento(requestAlunoDTO.dataNascimento());
        alunoEntity.setIdCadastro(requestAlunoDTO.idCadastro());
        alunoEntity.setIdTurma(requestAlunoDTO.idTurma());

        return alunoEntity;
    }

    @Override
    public ResponseAlunoDTO criarResponseAlunoDTO(AlunoEntity alunoEntitySalvo) {
        return new ResponseAlunoDTO(
                alunoEntitySalvo.getId(),
                alunoEntitySalvo.getNome(),
                alunoEntitySalvo.getDataNascimento(),
                alunoEntitySalvo.getIdCadastro(),
                alunoEntitySalvo.getIdTurma()
        );
    }

    @Override
    public List<AlunoEntity> listarAlunos() {
         return alunoRepository.findAll();

    }

    @Override
    public List<ResponseAlunoDTO> criarResponseAlunoDTO(List<AlunoEntity> alunoEntityList) {
        return alunoEntityList.stream()
                .map(this::criarResponseAlunoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AlunoEntity buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException("Id do Aluno não encontrado: " + id));
    }

    @Override
    public AlunoEntity atualizarAluno(Long id, RequestAlunoDTO requestAlunoDTO) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    aluno.setNome(requestAlunoDTO.nome());
                    aluno.setDataNascimento(requestAlunoDTO.dataNascimento());
                    aluno.setIdCadastro(requestAlunoDTO.idCadastro());
                    aluno.setIdTurma(requestAlunoDTO.idTurma());
                    return alunoRepository.save(aluno);
                })
                .orElseThrow(() -> new AlunoNotFoundException("Id do Aluno não encontrado para atualizar: " + id));
    }

    @Override
    public Void deletarAluno(Long id) {
        alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException("Id do Aluno não encontrado para deletar: " + id));
        alunoRepository.deleteById(id);
        return null;
    }


    @Override
    public ResponseAlunoListaDeNotasDTO criarResponseAlunoListaDeNotasDTO(List<NotaEntity> notasEntityList) {
        List<Long> valores = notasEntityList.stream()
                .map(NotaEntity::getValor)
                .collect(Collectors.toList());
        return new ResponseAlunoListaDeNotasDTO(
                notasEntityList.get(0).getIdAluno(),
                valores
        );
    }

    @Override
    public Long calcularPontuacaoDeAluno(List<NotaEntity> notaEntityList) {
        if (notaEntityList == null || notaEntityList.isEmpty()) {
            return 0L;
        }

        OptionalDouble averageOptional = notaEntityList.stream()
            .mapToLong(NotaEntity::getValor)
            .average();

        return Math.round(averageOptional.orElse(0.0));

    }

    @Override
    public List<AlunoEntity> buscarAlunosDeIdTurma(Long idTurma) {
        return alunoRepository.findAllByIdTurma(idTurma);
    }

    public ResponseAlunoPontuacaoDTO criarResponseAlunoPontuacaoDTO(AlunoEntity alunoEntity, Long pontuacao) {
        return new ResponseAlunoPontuacaoDTO(
                alunoEntity.getId(),
                pontuacao
        );
    }


}


