package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.requests.RequestCadastroDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCadastroDTO;
import com.fmt.educonnect.datasource.entities.CadastroEntity;
import com.fmt.educonnect.datasource.repositories.CadastroRepository;
import com.fmt.educonnect.interfaces.CadastroInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CadastroService implements CadastroInterface {

    @Autowired
    private CadastroRepository cadastroRepository;

    public CadastroService(CadastroRepository cadastroRepository) {
        this.cadastroRepository = cadastroRepository;
    }

    @Override
    public ResponseCadastroDTO criarCadastro(RequestCadastroDTO requestCadastroDTO) {
        CadastroEntity cadastroEntity = converterParaEntidade(requestCadastroDTO);
        CadastroEntity cadastroEntitySalvo = cadastroRepository.save(cadastroEntity);
        return converterParaResponseCadastroDTO(cadastroEntitySalvo);
    }

    public ResponseCadastroDTO converterParaResponseCadastroDTO(CadastroEntity cadastroEntitySalvo) {
        return new ResponseCadastroDTO(cadastroEntitySalvo.getId(), cadastroEntitySalvo.getLogin(), cadastroEntitySalvo.getRole());
    }

    @Override
    public CadastroEntity converterParaEntidade(RequestCadastroDTO requestCadastroDTO) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(requestCadastroDTO.password());
        CadastroEntity newCadastro = new CadastroEntity(requestCadastroDTO.login(), encryptedPassword, requestCadastroDTO.role());
        return newCadastro;
    }

    public List<ResponseCadastroDTO> listarCadastros() {
        List<CadastroEntity> cadastros = cadastroRepository.findAll();
        List<ResponseCadastroDTO> responseDTOsList = cadastros.stream()
                .map(user -> new ResponseCadastroDTO(user.getId(), user.getLogin(), user.getRole()))
                .collect(Collectors.toList());
        return responseDTOsList;
    }


}
