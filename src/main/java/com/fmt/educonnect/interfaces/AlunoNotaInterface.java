package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestAlunoDTO;
import com.fmt.educonnect.datasource.entities.AlunoEntity;
import com.fmt.educonnect.datasource.entities.CadastroEntity;
import com.fmt.educonnect.datasource.entities.NotaEntity;

import java.util.List;

public interface AlunoNotaInterface {


    List<NotaEntity> buscarNotasPorIdAluno(AlunoEntity alunoEntity);

    AlunoEntity buscarAlunoPorId(Long id);

    AlunoEntity criarAluno(RequestAlunoDTO requestAlunoDTO);

    CadastroEntity buscarCadastroPorLogin(String login);
}
