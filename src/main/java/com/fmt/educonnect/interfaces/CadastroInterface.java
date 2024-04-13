package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestCadastroDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCadastroDTO;
import com.fmt.educonnect.datasource.entities.CadastroEntity;
import com.fmt.educonnect.datasource.entities.PapelEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface CadastroInterface {


    ResponseCadastroDTO criarCadastro(RequestCadastroDTO requestCadastroDTO);

    ResponseCadastroDTO converterParaResponseCadastroDTO(CadastroEntity cadastroEntity);

    CadastroEntity converterParaEntidade(RequestCadastroDTO requestCadastroDTO, PapelEntity papelEntitySalvo);

    List<ResponseCadastroDTO> listarCadastros();

}
