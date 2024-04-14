package com.fmt.educonnect.services;

import com.fmt.educonnect.controllers.dtos.requests.RequestCadastroDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCadastroDTO;
import com.fmt.educonnect.datasource.entities.CadastroEntity;
import com.fmt.educonnect.datasource.entities.PapelEntity;
import com.fmt.educonnect.datasource.repositories.CadastroRepository;
import com.fmt.educonnect.datasource.repositories.PapelRepository;
import com.fmt.educonnect.infra.exceptions.PapelNotFoundException;
import com.fmt.educonnect.interfaces.CadastroInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        Optional<PapelEntity> optionalPapelEntity = converterParaPapelEntidade(requestCadastroDTO);

        PapelEntity papelEntity = optionalPapelEntity.orElseThrow(
                () -> new PapelNotFoundException("Número de papel inválido: " + requestCadastroDTO.idPapel())
        );

        CadastroEntity cadastroEntity = converterParaEntidade(requestCadastroDTO, papelEntity);
        CadastroEntity cadastroEntitySalvo = cadastroRepository.save(cadastroEntity);

        return converterParaResponseCadastroDTO(cadastroEntitySalvo);
    }

    public Optional<PapelEntity> converterParaPapelEntidade(RequestCadastroDTO requestCadastroDTO) {
        return papelRepository.findById(requestCadastroDTO.idPapel());
    }

    public ResponseCadastroDTO converterParaResponseCadastroDTO(CadastroEntity cadastroEntitySalvo) {
        return new ResponseCadastroDTO(cadastroEntitySalvo.getId(), cadastroEntitySalvo.getNome(), cadastroEntitySalvo.getLogin(), cadastroEntitySalvo.getIdPapel());
    }

    @Override
    public CadastroEntity converterParaEntidade(RequestCadastroDTO requestCadastroDTO, PapelEntity papelEntity) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(requestCadastroDTO.password());
        return new CadastroEntity(requestCadastroDTO.nome(), requestCadastroDTO.login(), encryptedPassword, papelEntity.getId());
    }

    public List<ResponseCadastroDTO> listarCadastros() {
        List<CadastroEntity> cadastros = cadastroRepository.findAll();
        return cadastros.stream()
                .map(user -> new ResponseCadastroDTO(user.getId(), user.getNome(), user.getLogin(), user.getIdPapel()))
                .collect(Collectors.toList());
    }
}
