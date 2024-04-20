package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.requests.RequestAlunoDTO;
import com.fmt.educonnect.datasource.entities.*;
import com.fmt.educonnect.interfaces.AlunoNotaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoNotaService implements AlunoNotaInterface {

    private final NotaService notaService;
    private final AlunoService alunoService;
    private final TurmaService turmaService;
    private final CadastroService cadastroService;
    private final LoginService loginService;

    @Autowired
    public AlunoNotaService(NotaService notaService,
                            AlunoService alunoService,
                            TurmaService turmaService,
                            CadastroService cadastroService,
                            LoginService loginService) {
        this.notaService = notaService;
        this.alunoService = alunoService;
        this.turmaService = turmaService;
        this.cadastroService = cadastroService;
        this.loginService = loginService;
    }

    @Override
    public List<NotaEntity> buscarNotasPorIdAluno(AlunoEntity alunoEntity) {
        return notaService.buscarNotasPorIdAluno(alunoEntity.getId());
    }

    @Override
    public AlunoEntity buscarAlunoPorId(Long id) {
        return alunoService.buscarAlunoPorId(id);
    }

    @Override
    public AlunoEntity criarAluno(RequestAlunoDTO requestAlunoDTO) {
        CadastroEntity cadastroEntity = cadastroService.buscarCadastroPorId(requestAlunoDTO.idCadastro());
        TurmaEntity turmaEntity = turmaService.buscarTurmaPorId(requestAlunoDTO.idTurma());
        AlunoEntity alunoEntity = alunoService.criarAlunoEntity(requestAlunoDTO);
        return alunoService.criarAluno(alunoEntity);
    }

    @Override
    public CadastroEntity buscarCadastroPorLogin(String login) {
        return (CadastroEntity) loginService.loadUserByUsername(login);
    }

}