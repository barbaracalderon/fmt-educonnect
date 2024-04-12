package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.DocenteDTO;
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
    public DocenteDTO criarDocente(DocenteDTO docenteDTO) {
        DocenteEntity docenteEntity = converterParaEntidade(docenteDTO);
        DocenteEntity docenteEntitySalvo = docenteRepository.save(docenteEntity);
        return converterParaResponseDTO(docenteEntitySalvo);
    }

    @Override
    public DocenteEntity converterParaEntidade(DocenteDTO docenteDTO){
        DocenteEntity docenteEntity = new DocenteEntity();

        docenteEntity.setNome(docenteDTO.getNome());
        docenteEntity.setDataEntrada(docenteDTO.getDataEntrada());
        docenteEntity.setIdUser(docenteDTO.getIdUsuario());

        return docenteEntity;
    }

    @Override
    public DocenteDTO converterParaResponseDTO(DocenteEntity docenteEntity){
        DocenteDTO docenteDTO = new DocenteDTO();

        docenteDTO.setId(docenteEntity.getId());
        docenteDTO.setNome(docenteEntity.getNome());
        docenteDTO.setDataEntrada(docenteEntity.getDataEntrada());
        docenteDTO.setIdUsuario(docenteEntity.getIdUser());

        return docenteDTO;
    }

    @Override
    public List<DocenteDTO> listarDocentes() {
        List<DocenteEntity> docentes = docenteRepository.findAll();
        List<DocenteDTO> docentesDTO = converterParaListaDeResponseDTO(docentes);
        return docentesDTO;
    }

    @Override
    public List<DocenteDTO> converterParaListaDeResponseDTO(List<DocenteEntity> docentes) {
        return docentes.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }
}
