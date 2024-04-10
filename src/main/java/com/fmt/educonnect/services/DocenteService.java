package com.fmt.educonnect.services;

import com.fmt.educonnect.interfaces.dtos.DocenteDTO;
import com.fmt.educonnect.interfaces.IDocenteService;
import com.fmt.educonnect.models.DocenteModel;
import com.fmt.educonnect.repositories.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocenteService implements IDocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Override
    public DocenteDTO criarDocente(DocenteDTO docenteDTO) {
        DocenteModel docenteModel = converterParaEntidade(docenteDTO);
        DocenteModel docenteModelSalvo = docenteRepository.save(docenteModel);
        return converterParaResponseDTO(docenteModelSalvo);
    }

    @Override
    public DocenteModel converterParaEntidade(DocenteDTO docenteDTO){
        DocenteModel docenteModel = new DocenteModel();

        docenteModel.setNome(docenteDTO.getNome());
        docenteModel.setDataEntrada(docenteDTO.getDataEntrada());
        docenteModel.setIdUsuario(docenteDTO.getIdUsuario());

        return docenteModel;
    }

    @Override
    public DocenteDTO converterParaResponseDTO(DocenteModel docenteModel){
        DocenteDTO docenteDTO = new DocenteDTO();

        docenteDTO.setId(docenteModel.getId());
        docenteDTO.setNome(docenteModel.getNome());
        docenteDTO.setDataEntrada(docenteModel.getDataEntrada());
        docenteDTO.setIdUsuario(docenteModel.getIdUsuario());

        return docenteDTO;
    }

    @Override
    public List<DocenteDTO> listarDocentes() {
        List<DocenteModel> docentes = docenteRepository.findAll();
        List<DocenteDTO> docentesDTO = converterParaListaDeResponseDTO(docentes);
        return docentesDTO;
    }

    @Override
    public List<DocenteDTO> converterParaListaDeResponseDTO(List<DocenteModel> docentes) {
        return docentes.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }
}
