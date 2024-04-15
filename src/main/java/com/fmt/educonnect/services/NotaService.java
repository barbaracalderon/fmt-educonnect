package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.requests.RequestNotaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseNotaDTO;
import com.fmt.educonnect.datasource.entities.*;
import com.fmt.educonnect.datasource.repositories.*;
import com.fmt.educonnect.infra.exceptions.AlunoNotFoundException;
import com.fmt.educonnect.infra.exceptions.DocenteNotFoundException;
import com.fmt.educonnect.infra.exceptions.NotaNotFoundException;
import com.fmt.educonnect.interfaces.NotaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotaService implements NotaInterface {


    private final NotaRepository notaRepository;
    private final MateriaRepository materiaRepository;
    private final DocenteRepository docenteRepository;
    private final AlunoRepository alunoRepository;

    @Autowired
    public NotaService(NotaRepository notaRepository,
                        MateriaRepository materiaRepository,
                        DocenteRepository docenteRepository,
                        AlunoRepository alunoRepository
    ) {
        this.notaRepository = notaRepository;
        this.materiaRepository = materiaRepository;
        this.docenteRepository = docenteRepository;
        this.alunoRepository = alunoRepository;
    }

    @Override
    public ResponseNotaDTO criarNota(RequestNotaDTO requestNotaDTO) {

        Optional<AlunoEntity> optionalAlunoEntity = alunoRepository.findById(requestNotaDTO.idAluno());
        AlunoEntity alunoEntity = optionalAlunoEntity.orElseThrow(
                () -> new AlunoNotFoundException("Id do Aluno inválido: " + requestNotaDTO.idAluno())
        );

        Optional<DocenteEntity> optionalDocenteEntity = docenteRepository.findById(requestNotaDTO.idDocente());
        DocenteEntity docenteEntity = optionalDocenteEntity.orElseThrow(
                () -> new DocenteNotFoundException("Id do Docente inválido: " + requestNotaDTO.idDocente())
        );

        Optional<MateriaEntity> optionalMateriaEntity = materiaRepository.findById(requestNotaDTO.idMateria());
        MateriaEntity materiaEntity = optionalMateriaEntity.orElseThrow(
                () -> new DocenteNotFoundException("Id do Docente inválido: " + requestNotaDTO.idMateria())
        );

        NotaEntity notaEntity = converterParaEntidade(requestNotaDTO);
        NotaEntity notaEntitySalvo = notaRepository.save(notaEntity);
        return converterParaResponseDTO(notaEntitySalvo);
    }

    @Override
    public NotaEntity converterParaEntidade(RequestNotaDTO requestNotaDTO){
        NotaEntity notaEntity = new NotaEntity();

        notaEntity.setDataLancamento(requestNotaDTO.dataLancamento());
        notaEntity.setIdAluno(requestNotaDTO.idAluno());
        notaEntity.setIdDocente(requestNotaDTO.idDocente());
        notaEntity.setIdMateria(requestNotaDTO.idMateria());
        notaEntity.setValor(requestNotaDTO.valor());

        return notaEntity;
    }

    @Override
    public ResponseNotaDTO converterParaResponseDTO(NotaEntity notaEntitySalvo){
        return new ResponseNotaDTO(
                notaEntitySalvo.getId(),
                notaEntitySalvo.getDataLancamento(),
                notaEntitySalvo.getIdAluno(),
                notaEntitySalvo.getIdDocente(),
                notaEntitySalvo.getIdMateria(),
                notaEntitySalvo.getValor()
        );
    }


    @Override
    public List<ResponseNotaDTO> listarNotas() {
        List<NotaEntity> notaEntityList = notaRepository.findAll();
        return converterParaListaDeResponseDTO(notaEntityList);
    }

    @Override
    public List<ResponseNotaDTO> converterParaListaDeResponseDTO(List<NotaEntity> notaEntityList) {
        return notaEntityList.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseNotaDTO buscarNotaPorId(Long id) {
        return notaRepository.findById(id)
                .map(this::converterParaResponseDTO)
                .orElseThrow(() -> new NotaNotFoundException("Id da Nota não encontrado: " + id));
    }

    @Override
    public ResponseNotaDTO atualizarNota(Long id, RequestNotaDTO requestNotaDTO) {
        return notaRepository.findById(id)
                .map(nota -> {
                    nota.setDataLancamento(requestNotaDTO.dataLancamento());
                    nota.setIdAluno(requestNotaDTO.idAluno());
                    nota.setIdDocente(requestNotaDTO.idDocente());
                    nota.setIdMateria(requestNotaDTO.idMateria());
                    nota.setValor(requestNotaDTO.valor());
                    NotaEntity updatedNota = notaRepository.save(nota);
                    return converterParaResponseDTO(updatedNota);
                })
                .orElseThrow(() -> new NotaNotFoundException("Id da Nota não encontrado para atualizar: " + id));
    }

    @Override
    public Void deletarNota(Long id) {
        notaRepository.findById(id)
                .orElseThrow(() -> new NotaNotFoundException("Id da Nota não encontrado para deletar: " + id));
        notaRepository.deleteById(id);
        return null;
    }

    @Override
    public List<ResponseNotaDTO> buscarNotasDeAlunoId(Long idAluno) {
        List<NotaEntity> notasEntityList = notaRepository.findAllByIdAluno(idAluno);
        if (notasEntityList.isEmpty()) {
            throw new AlunoNotFoundException("Id do Aluno não encontrado: " + idAluno);
        } else {
            return converterParaListaDeResponseDTO(notasEntityList);
        }
    }

}
