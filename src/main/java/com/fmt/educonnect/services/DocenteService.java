package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.requests.RequestDocenteDTO;
import com.fmt.educonnect.datasource.entities.DocenteEntity;
import com.fmt.educonnect.datasource.repositories.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocenteService implements com.fmt.educonnect.interfaces.DocenteInterface {

    @Autowired
    private DocenteRepository docenteRepository;

    public DocenteService(DocenteRepository docenteRepository) {
        this.docenteRepository = docenteRepository;
    }

    @Override
    public RequestDocenteDTO criarDocente(RequestDocenteDTO requestDocenteDTO) {
        DocenteEntity docenteEntity = converterParaEntidade(requestDocenteDTO);
        DocenteEntity docenteEntitySalvo = docenteRepository.save(docenteEntity);
        return converterParaResponseDTO(docenteEntitySalvo);
    }

    @Override
    public DocenteEntity converterParaEntidade(RequestDocenteDTO requestDocenteDTO){
        DocenteEntity docenteEntity = new DocenteEntity();

        docenteEntity.setNome(requestDocenteDTO.getNome());
        docenteEntity.setDataEntrada(requestDocenteDTO.getDataEntrada());
        docenteEntity.setIdUser(requestDocenteDTO.getIdUsuario());

        return docenteEntity;
    }

    @Override
    public RequestDocenteDTO converterParaResponseDTO(DocenteEntity docenteEntity){
        RequestDocenteDTO requestDocenteDTO = new RequestDocenteDTO();

        requestDocenteDTO.setId(docenteEntity.getId());
        requestDocenteDTO.setNome(docenteEntity.getNome());
        requestDocenteDTO.setDataEntrada(docenteEntity.getDataEntrada());
        requestDocenteDTO.setIdUsuario(docenteEntity.getIdUser());

        return requestDocenteDTO;
    }

    @Override
    public List<RequestDocenteDTO> listarDocentes() {
        List<DocenteEntity> docentes = docenteRepository.findAll();
        List<RequestDocenteDTO> docentesDTO = converterParaListaDeResponseDTO(docentes);
        return docentesDTO;
    }

    @Override
    public List<RequestDocenteDTO> converterParaListaDeResponseDTO(List<DocenteEntity> docentes) {
        return docentes.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }
}
