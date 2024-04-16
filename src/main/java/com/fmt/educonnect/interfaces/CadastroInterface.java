package com.fmt.educonnect.interfaces;

import com.fmt.educonnect.controllers.dtos.requests.RequestCadastroDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCadastroDTO;
import com.fmt.educonnect.datasource.entities.CadastroEntity;
import com.fmt.educonnect.datasource.entities.PapelEntity;

import java.util.List;

public interface CadastroInterface {


    CadastroEntity criarCadastro(RequestCadastroDTO requestCadastroDTO);

    List<CadastroEntity> listarCadastros();

    Void deletarCadastro(Long id);
}
