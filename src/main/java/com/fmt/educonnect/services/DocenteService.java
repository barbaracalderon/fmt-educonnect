package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.requests.RequestDocenteDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseDocenteDTO;
import com.fmt.educonnect.datasource.entities.CadastroEntity;
import com.fmt.educonnect.datasource.entities.DocenteEntity;
import com.fmt.educonnect.datasource.repositories.DocenteRepository;
import com.fmt.educonnect.infra.exceptions.CadastroNotFoundException;
import com.fmt.educonnect.infra.exceptions.DocenteNotFoundException;
import com.fmt.educonnect.interfaces.DocenteInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocenteService implements DocenteInterface {


    private final DocenteRepository docenteRepository;
    private final CadastroService cadastroService;

    @Autowired
    public DocenteService(DocenteRepository docenteRepository, CadastroService cadastroService) {
        this.docenteRepository = docenteRepository;
        this.cadastroService = cadastroService;
    }

    public DocenteEntity criarDocente(RequestDocenteDTO requestDocenteDTO) {
        CadastroEntity cadastroEntity = cadastroService.buscarCadastroPorId(requestDocenteDTO.idCadastro());
        DocenteEntity docenteEntity = criarDocenteEntity(requestDocenteDTO);
        return docenteRepository.save(docenteEntity);
    }

    @Override
    public DocenteEntity criarDocenteEntity(RequestDocenteDTO requestDocenteDTO) {
        DocenteEntity docenteEntity = new DocenteEntity();

        docenteEntity.setNome(requestDocenteDTO.nome());
        docenteEntity.setDataEntrada(requestDocenteDTO.dataEntrada());
        docenteEntity.setIdCadastro(requestDocenteDTO.idCadastro());

        return docenteEntity;
    }

    @Override
    public ResponseDocenteDTO criarResponseDocenteDTO(DocenteEntity docenteEntitySalvo){
        return new ResponseDocenteDTO(
                docenteEntitySalvo.getId(),
                docenteEntitySalvo.getNome(),
                docenteEntitySalvo.getDataEntrada(),
                docenteEntitySalvo.getIdCadastro()
        );
    }

    @Override
    public List<DocenteEntity> listarDocentes() {
        return docenteRepository.findAll();
    }


    @Override
    public List<ResponseDocenteDTO> criarResponseDocenteDTO(List<DocenteEntity> docenteEntityList) {
        return docenteEntityList.stream()
                .map(this::criarResponseDocenteDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DocenteEntity buscarDocentePorId(Long id) {
        return docenteRepository.findById(id).orElseThrow(
                () -> new DocenteNotFoundException("Id do Docente não encontrado: " + id)
        );
    }

    @Override
    public DocenteEntity atualizarDocente(Long id, RequestDocenteDTO requestDocenteDTO) {
        return docenteRepository.findById(id)
                .map(docente -> {
                    docente.setNome(requestDocenteDTO.nome());
                    docente.setDataEntrada(requestDocenteDTO.dataEntrada());
                    return docente;
                })
                .orElseThrow(
                        () -> new DocenteNotFoundException("Id do Docente não encontrado para atualizar: " + id)
                );
    }

    @Override
    public Void deletarDocente(Long id) {
        docenteRepository.findById(id)
                .orElseThrow(() -> new DocenteNotFoundException("Id do Docente não encontrado para deletar: " + id));
        docenteRepository.deleteById(id);
        return null;
    }

}
