package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestAlunoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseAlunoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseAlunoListaDeNotasDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseAlunoPontuacaoDTO;
import com.fmt.educonnect.infra.exceptions.*;
import com.fmt.educonnect.services.AlunoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping
    public ResponseEntity<?> criarAluno(@RequestBody RequestAlunoDTO requestAlunoDTO) {
        try {
            log.info("POST /alunos ---> Chamada para o método.");
            ResponseAlunoDTO responseAlunoDTO = alunoService.criarAluno(requestAlunoDTO);
            log.info("POST /alunos ---> Sucesso.");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseAlunoDTO);
        } catch (CadastroNotFoundException | TurmaNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<ResponseAlunoDTO>> listarAlunos() {
        log.info("GET /alunos ---> Chamada para o método.");
        List<ResponseAlunoDTO> ResponseAlunoDTOsList = alunoService.listarAlunos();
        if (ResponseAlunoDTOsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("GET /alunos ---> Sucesso.");
            return ResponseEntity.ok().body(ResponseAlunoDTOsList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarAlunoPorId(@PathVariable("id") Long id) {
        try {
            log.info("GET /alunos/{} ---> Chamada para o método.", id);
            ResponseAlunoDTO ResponseAlunoDTO = alunoService.buscarAlunoPorId(id);
            log.info("GET /alunos/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseAlunoDTO);
        } catch (AlunoNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAluno(@PathVariable("id") Long id, @RequestBody RequestAlunoDTO RequestAlunoDTO) {
        try {
            log.info("PUT /alunos/{} ---> Chamada para o método.", id);
            ResponseAlunoDTO ResponseAlunoDTO = alunoService.atualizarAluno(id, RequestAlunoDTO);
            log.info("PUT /alunos/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseAlunoDTO);
        } catch (DocenteNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAluno(@PathVariable("id") Long id) {
        try {
            log.info("DELETE /alunos/{} ---> Chamada para o método.", id);
            alunoService.deletarAluno(id);
            log.info("DELETE /alunos/{} ---> Sucesso.", id);
            return ResponseEntity.noContent().build();
        } catch (DocenteNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/notas")
    public ResponseEntity<?> buscarNotasDeAlunoId(@PathVariable("id") Long id) {
        try {
            log.info("GET /alunos/{}/notas ---> Chamada para o método.", id);
            ResponseAlunoDTO responseAlunoDTO = alunoService.buscarAlunoPorId(id);
            ResponseAlunoListaDeNotasDTO responseAlunoListaDeNotasDTO = alunoService.buscarNotasDeAluno(responseAlunoDTO);
            log.info("GET /alunos/{}/notas ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseAlunoListaDeNotasDTO);
        } catch (NotaNotFoundException | AlunoNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/pontuacao")
    public ResponseEntity<?> buscarPontuacaoDeAlunoId(@PathVariable("id") Long id) {
        try {
            log.info("GET /alunos/{}/pontuação ---> Chamada para o método.", id);
            ResponseAlunoDTO responseAlunoDTO = alunoService.buscarAlunoPorId(id);
            ResponseAlunoListaDeNotasDTO responseAlunoListaDeNotasDTO = alunoService.buscarNotasDeAluno(responseAlunoDTO);
            ResponseAlunoPontuacaoDTO responseAlunoPontuacaoDTO = alunoService.calcularPontuacaoDeAluno(responseAlunoListaDeNotasDTO);
            log.info("GET /alunos/{}/pontuação ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseAlunoPontuacaoDTO);
        } catch (NotaNotFoundException | AlunoNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
