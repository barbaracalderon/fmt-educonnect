package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestCadastroDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCadastroDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseDocenteDTO;
import com.fmt.educonnect.datasource.entities.CadastroEntity;

import java.util.List;

public interface CadastroInterface {

    CadastroEntity converterParaEntidade(RequestCadastroDTO requestCadastroDTO);

    ResponseCadastroDTO criarCadastro(RequestCadastroDTO requestCadastroDTO);

    ResponseCadastroDTO converterParaResponseCadastroDTO(CadastroEntity cadastroEntity);

    List<ResponseCadastroDTO> listarCadastros();

}
