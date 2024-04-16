package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.datasource.entities.AlunoEntity;
import com.fmt.educonnect.datasource.entities.NotaEntity;

import java.util.List;

public interface AlunoNotaInterface {


    List<NotaEntity> buscarNotasPorIdAluno(AlunoEntity alunoEntity);

    AlunoEntity buscarAlunoPorId(Long id);
}
