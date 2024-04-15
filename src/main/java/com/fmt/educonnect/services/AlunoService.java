package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.requests.RequestAlunoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseAlunoDTO;
import com.fmt.educonnect.datasource.entities.AlunoEntity;
import com.fmt.educonnect.datasource.entities.CadastroEntity;
import com.fmt.educonnect.datasource.repositories.AlunoRepository;
import com.fmt.educonnect.datasource.repositories.CadastroRepository;
import com.fmt.educonnect.infra.exceptions.AlunoNotFoundException;
import com.fmt.educonnect.infra.exceptions.CadastroNotFoundException;
import com.fmt.educonnect.interfaces.AlunoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlunoService implements AlunoInterface {

    private AlunoRepository alunoRepository;
    private CadastroRepository cadastroRepository;

    @Autowired
    public AlunoService(AlunoRepository alunoRepository, CadastroRepository cadastroRepository) {
        this.alunoRepository = alunoRepository;
        this.cadastroRepository = cadastroRepository;
    }


    @Override
    public ResponseAlunoDTO criarAluno(RequestAlunoDTO requestAlunoDTO) {

        Optional<CadastroEntity> optionalCadastroEntity = cadastroRepository.findById(requestAlunoDTO.idCadastro());

        CadastroEntity cadastroEntity = optionalCadastroEntity.orElseThrow(
                () -> new CadastroNotFoundException("Número de cadastro inválido: " + requestAlunoDTO.idCadastro())
        );

        AlunoEntity alunoEntity = converterParaEntidade(requestAlunoDTO);
        AlunoEntity alunoEntitySalvo = alunoRepository.save(alunoEntity);
        return converterParaResponseDTO(alunoEntitySalvo);
    }

    @Override
    public AlunoEntity converterParaEntidade(RequestAlunoDTO requestAlunoDTO) {
        AlunoEntity alunoEntity = new AlunoEntity();

        alunoEntity.setNome(requestAlunoDTO.nome());
        alunoEntity.setDataNascimento(requestAlunoDTO.dataNascimento());
        alunoEntity.setIdCadastro(requestAlunoDTO.idCadastro());

        return alunoEntity;
    }

    @Override
    public ResponseAlunoDTO converterParaResponseDTO(AlunoEntity alunoEntitySalvo) {
        return new ResponseAlunoDTO(
                alunoEntitySalvo.getId(),
                alunoEntitySalvo.getNome(),
                alunoEntitySalvo.getDataNascimento(),
                alunoEntitySalvo.getIdCadastro()
        );
    }

    @Override
    public List<ResponseAlunoDTO> listarAlunos() {
        List<AlunoEntity> alunoEntityList = alunoRepository.findAll();
        return converterParaListaDeResponseDTO(alunoEntityList);
    }

    @Override
    public List<ResponseAlunoDTO> converterParaListaDeResponseDTO(List<AlunoEntity> alunoEntityList) {
        return alunoEntityList.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseAlunoDTO buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id)
                .map(this::converterParaResponseDTO)
                .orElseThrow(() -> new AlunoNotFoundException("Id do Aluno não encontrado: " + id));
    }

    @Override
    public ResponseAlunoDTO atualizarAluno(Long id, RequestAlunoDTO requestAlunoDTO) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    aluno.setNome(requestAlunoDTO.nome());
                    aluno.setDataNascimento(requestAlunoDTO.dataNascimento());
                    aluno.setIdCadastro(requestAlunoDTO.idCadastro());
                    AlunoEntity updatedAluno = alunoRepository.save(aluno);
                    return converterParaResponseDTO(updatedAluno);
                })
                .orElseThrow(() -> new AlunoNotFoundException("Id do Aluno não encontrado para atualizar: " + id));
    }

    @Override
    public void deletarAluno(Long id) {
        alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException("Id do Aluno não encontrado para deletar: " + id));
        alunoRepository.deleteById(id);
    }
}
