package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.requests.RequestDocenteDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseDocenteDTO;
import com.fmt.educonnect.datasource.entities.DocenteEntity;
import com.fmt.educonnect.datasource.repositories.DocenteRepository;
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
        List<DocenteEntity> docentes = docenteRepository.findAll();
        List<ResponseDocenteDTO> responseDocentesDTOList = converterParaListaDeResponseDTO(docentes);
        return responseDocentesDTOList;
    }

    @Override
    public List<ResponseDocenteDTO> converterParaListaDeResponseDTO(List<DocenteEntity> docentes) {
        return docentes.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }
}
