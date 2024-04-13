package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestAlunoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseAlunoDTO;
import com.fmt.educonnect.datasource.entities.AlunoEntity;

import java.util.List;

public interface AlunoInterface {

    ResponseAlunoDTO criarAluno(RequestAlunoDTO requestAlunoDTO);

    AlunoEntity converterParaEntidade(RequestAlunoDTO requestAlunoDTO);

    ResponseAlunoDTO converterParaResponseDTO(AlunoEntity alunoEntity);

    List<ResponseAlunoDTO> listarAlunos();

    List<ResponseAlunoDTO> converterParaListaDeResponseDTO(List<AlunoEntity> alunos);

    ResponseAlunoDTO buscarAlunoPorId(Long id);


    ResponseAlunoDTO atualizarAluno(Long id, RequestAlunoDTO requestAlunoDTO);

    void deletarAluno(Long id);


}
