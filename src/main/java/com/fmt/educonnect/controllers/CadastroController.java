package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestCadastroDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCadastroDTO;
import com.fmt.educonnect.infra.exceptions.PapelNotFoundException;
import com.fmt.educonnect.services.CadastroService;
import com.fmt.educonnect.services.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cadastro")
public class CadastroController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private CadastroService cadastroService;


    @PostMapping()
    public ResponseEntity<?> criarCadastro(@RequestBody @Valid RequestCadastroDTO requestCadastroDTO) {
        if (loginService.loadUserByUsername(requestCadastroDTO.login()) != null) {
            return ResponseEntity.badRequest().build();

        } else {
            try {
                ResponseCadastroDTO responseCadastroDTO = cadastroService.criarCadastro(requestCadastroDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(responseCadastroDTO);
            } catch (PapelNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }
    }

    @GetMapping()
    public ResponseEntity<List<ResponseCadastroDTO>> listarCadastros() {
        List<ResponseCadastroDTO> responseCadastroDTOsList = cadastroService.listarCadastros();
        return ResponseEntity.ok(responseCadastroDTOsList);
    }


}
