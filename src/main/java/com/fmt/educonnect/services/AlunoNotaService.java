package com.fmt.educonnect.services;

import com.fmt.educonnect.datasource.entities.*;
import com.fmt.educonnect.interfaces.AlunoNotaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoNotaService implements AlunoNotaInterface {

    @Autowired
    private final NotaService notaService;
    @Autowired
    private final AlunoService alunoService;


    public AlunoNotaService(NotaService notaService, AlunoService alunoService) {
        this.notaService = notaService;
        this.alunoService = alunoService;
    }

    @Override
    public List<NotaEntity> buscarNotasPorIdAluno(AlunoEntity alunoEntity) {
        return notaService.buscarNotasPorIdAluno(alunoEntity.getId());
    }

    @Override
    public AlunoEntity buscarAlunoPorId(Long id) {
        return alunoService.buscarAlunoPorId(id);
    }




}