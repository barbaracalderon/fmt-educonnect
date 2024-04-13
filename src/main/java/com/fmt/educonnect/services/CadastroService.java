package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.requests.RequestCadastroDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCadastroDTO;
import com.fmt.educonnect.datasource.entities.CadastroEntity;
import com.fmt.educonnect.datasource.entities.PapelEntity;
import com.fmt.educonnect.datasource.repositories.CadastroRepository;
import com.fmt.educonnect.datasource.repositories.PapelRepository;
import com.fmt.educonnect.interfaces.CadastroInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CadastroService implements CadastroInterface {

    private final CadastroRepository cadastroRepository;
    private final PapelRepository papelRepository;

    @Autowired
    public CadastroService(CadastroRepository cadastroRepository, PapelRepository papelRepository) {
        this.cadastroRepository = cadastroRepository;
        this.papelRepository = papelRepository;
    }

    @Override
    public ResponseCadastroDTO criarCadastro(RequestCadastroDTO requestCadastroDTO) {
        PapelEntity papelEntity = converterParaPapelEntidade(requestCadastroDTO);
        PapelEntity papelEntitySalvo = papelRepository.save(papelEntity);

        CadastroEntity cadastroEntity = converterParaEntidade(requestCadastroDTO, papelEntitySalvo);
        CadastroEntity cadastroEntitySalvo = cadastroRepository.save(cadastroEntity);

        return converterParaResponseCadastroDTO(cadastroEntitySalvo);
    }

    public PapelEntity converterParaPapelEntidade(RequestCadastroDTO requestCadastroDTO) {
        return new PapelEntity(requestCadastroDTO.nomePapel().toString());
    }

    public ResponseCadastroDTO converterParaResponseCadastroDTO(CadastroEntity cadastroEntitySalvo) {
        return new ResponseCadastroDTO(cadastroEntitySalvo.getId(), cadastroEntitySalvo.getNome(), cadastroEntitySalvo.getLogin(), cadastroEntitySalvo.getIdPapel());
    }

    @Override
    public CadastroEntity converterParaEntidade(RequestCadastroDTO requestCadastroDTO, PapelEntity papelEntitySalvo) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(requestCadastroDTO.password());
        return new CadastroEntity(requestCadastroDTO.nome(), requestCadastroDTO.login(), encryptedPassword, papelEntitySalvo.getId());
    }

    public List<ResponseCadastroDTO> listarCadastros() {
        List<CadastroEntity> cadastros = cadastroRepository.findAll();
        return cadastros.stream()
                .map(user -> new ResponseCadastroDTO(user.getId(), user.getNome(), user.getLogin(), user.getIdPapel()))
                .collect(Collectors.toList());
    }
}
