package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.requests.RequestDocenteDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseDocenteDTO;
import com.fmt.educonnect.datasource.entities.DocenteEntity;
import com.fmt.educonnect.datasource.repositories.DocenteRepository;
import com.fmt.educonnect.infra.exceptions.DocenteNotFoundException;
import com.fmt.educonnect.interfaces.DocenteInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocenteService implements DocenteInterface {

    @Autowired
    private DocenteRepository docenteRepository;

    public DocenteService(DocenteRepository docenteRepository) {
        this.docenteRepository = docenteRepository;
    }

    @Override
    public ResponseDocenteDTO criarDocente(RequestDocenteDTO requestDocenteDTO) {
        DocenteEntity docenteEntity = converterParaEntidade(requestDocenteDTO);
        DocenteEntity docenteEntitySalvo = docenteRepository.save(docenteEntity);
        return converterParaResponseDTO(docenteEntitySalvo);
    }

    @Override
    public DocenteEntity converterParaEntidade(RequestDocenteDTO requestDocenteDTO){
        DocenteEntity docenteEntity = new DocenteEntity();

        docenteEntity.setNome(requestDocenteDTO.nome());
        docenteEntity.setDataEntrada(requestDocenteDTO.dataEntrada());
        docenteEntity.setIdCadastro(requestDocenteDTO.idCadastro());

        return docenteEntity;
    }

    @Override
    public ResponseDocenteDTO converterParaResponseDTO(DocenteEntity docenteEntitySalvo){
        return new ResponseDocenteDTO(
                docenteEntitySalvo.getId(),
                docenteEntitySalvo.getNome(),
                docenteEntitySalvo.getDataEntrada(),
                docenteEntitySalvo.getIdCadastro()
        );
    }

    @Override
    public List<ResponseDocenteDTO> listarDocentes() {
        List<DocenteEntity> docenteEntityList = docenteRepository.findAll();
        return converterParaListaDeResponseDTO(docenteEntityList);
    }

    @Override
    public List<ResponseDocenteDTO> converterParaListaDeResponseDTO(List<DocenteEntity> docenteEntityList) {
        return docenteEntityList.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDocenteDTO buscarDocentePorId(Long id) {
        return docenteRepository.findById(id)
                .map(this::converterParaResponseDTO)
                .orElseThrow(() -> new DocenteNotFoundException("Id do Docente não encontrado: " + id));
    }

    @Override
    public ResponseDocenteDTO atualizarDocente(Long id, RequestDocenteDTO requestDocenteDTO) {
        return docenteRepository.findById(id)
                .map(docente -> {
                    docente.setNome(requestDocenteDTO.nome());
                    docente.setDataEntrada(requestDocenteDTO.dataEntrada());
                    DocenteEntity updatedDocente = docenteRepository.save(docente);
                    return converterParaResponseDTO(updatedDocente);
                })
                .orElseThrow(() -> new DocenteNotFoundException("Id do Docente não encontrado para atualizar: " + id));
    }

    @Override
    public Void deletarDocente(Long id) {
        docenteRepository.findById(id)
                .orElseThrow(() -> new DocenteNotFoundException("Id do Docente não encontrado para deletar: " + id));
        docenteRepository.deleteById(id);
        return null;
    }



}
