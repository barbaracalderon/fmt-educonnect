package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestCadastroDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCadastroDTO;
import com.fmt.educonnect.infra.exceptions.DocenteNotFoundException;
import com.fmt.educonnect.infra.exceptions.PapelNotFoundException;
import com.fmt.educonnect.services.CadastroService;
import com.fmt.educonnect.services.LoginService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
                log.info("POST /cadastro ---> Chamada para o método.");
                ResponseCadastroDTO responseCadastroDTO = cadastroService.criarCadastro(requestCadastroDTO);
                log.info("POST /cadastro ---> Sucesso.");
                return ResponseEntity.status(HttpStatus.CREATED).body(responseCadastroDTO);
            } catch (PapelNotFoundException e) {
                log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }
    }

    @GetMapping()
    public ResponseEntity<List<ResponseCadastroDTO>> listarCadastros() {
        log.info("GET /cadastro ---> Chamada para o método.");
        List<ResponseCadastroDTO> responseCadastroDTOsList = cadastroService.listarCadastros();
        log.info("GET /cadastro ---> Sucesso.");
        return ResponseEntity.ok(responseCadastroDTOsList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCadastro(@PathVariable("id") Long id) {
        try {
            log.info("DELETE /cadastro/{} ---> Chamada para o método.", id);
            cadastroService.deletarCadastro(id);
            log.info("DELETE /cadastro ---> Sucesso.");
            return ResponseEntity.noContent().build();
        } catch (DocenteNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
