package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestAlunoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseAlunoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseAlunoListaDeNotasDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseAlunoPontuacaoDTO;
import com.fmt.educonnect.datasource.entities.AlunoEntity;
import com.fmt.educonnect.datasource.entities.NotaEntity;

import java.util.List;

public interface AlunoInterface {

    AlunoEntity criarAluno(AlunoEntity alunoEntity);

    AlunoEntity criarAlunoEntity(RequestAlunoDTO requestAlunoDTO);

    ResponseAlunoDTO criarResponseAlunoDTO(AlunoEntity alunoEntitySalvo);

    List<ResponseAlunoDTO> criarResponseAlunoDTO(List<AlunoEntity> alunoEntityList);

    ResponseAlunoListaDeNotasDTO criarResponseAlunoListaDeNotasDTO(List<NotaEntity> notasEntityList);

    List<AlunoEntity> listarAlunos();

    AlunoEntity buscarAlunoPorId(Long id);

    AlunoEntity atualizarAluno(Long id, RequestAlunoDTO requestAlunoDTO);

    Void deletarAluno(Long id);

    Long calcularPontuacaoDeAluno(List<NotaEntity> notaEntityList);

    List<AlunoEntity> buscarAlunosDeIdTurma(Long idTurma);
}
