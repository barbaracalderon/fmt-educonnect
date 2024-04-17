package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestNotaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseNotaDTO;
import com.fmt.educonnect.datasource.entities.NotaEntity;
import com.fmt.educonnect.infra.exceptions.*;
import com.fmt.educonnect.services.NotaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("notas")
public class NotaController {

    private final NotaService notaService;

    @Autowired
    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    @PostMapping()
    public ResponseEntity<?> criarNota(@Valid @RequestBody RequestNotaDTO requestNotaDTO) {
        try {
            log.info("POST /notas ---> Chamada para o método.");
            NotaEntity notaEntity = notaService.criarNota(requestNotaDTO);
            ResponseNotaDTO responseNotaDTO = notaService.criarResponseNotaDTO(notaEntity);
            log.info("POST /notas ---> Sucesso.");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseNotaDTO);
        } catch (AlunoNotFoundException | DocenteNotFoundException | MateriaNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping()
    public ResponseEntity<List<ResponseNotaDTO>> listarNotas() {
        log.info("GET /notas ---> Chamada para o método.");
        List<NotaEntity> notaEntityList = notaService.listarNotas();
        if (notaEntityList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            List<ResponseNotaDTO> ResponseNotaDTOsList = notaService.criarResponseNotaDTO(notaEntityList);
            log.info("GET /notas ---> Sucesso.");
            return ResponseEntity.ok().body(ResponseNotaDTOsList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarNotaPorId(@PathVariable("id") Long id) {
        try {
            log.info("GET /notas/{} ---> Chamada para o método.", id);
            NotaEntity notaEntity = notaService.buscarNotaPorId(id);
            ResponseNotaDTO responseNotaDTO = notaService.criarResponseNotaDTO(notaEntity);
            log.info("GET /notas/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseNotaDTO);
        } catch (NotaNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarNota(@PathVariable("id") Long id, @Valid @RequestBody RequestNotaDTO requestNotaDTO) {
        try {
            log.info("PUT /notas/{} ---> Chamada para o método.", id);
            NotaEntity notaEntity = notaService.atualizarNota(id, requestNotaDTO);
            ResponseNotaDTO responseNotaDTO = notaService.criarResponseNotaDTO(notaEntity);
            log.info("PUT /notas/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseNotaDTO);
        } catch (NotaNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarNota(@PathVariable("id") Long id) {
        try {
            log.info("DELETE /notas/{} ---> Chamada para o método.", id);
            notaService.deletarNota(id);
            log.info("DELETE /notas/{} ---> Sucesso.", id);
            return ResponseEntity.noContent().build();
        } catch (NotaNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/alunos/{idAluno}")
    public ResponseEntity<?> buscarNotasDeAlunoId(@PathVariable("idAluno") Long idAluno) {
        try {
            log.info("GET /notas/alunos/{} ---> Chamada para o método.", idAluno);
            List<NotaEntity> notaEntityList = notaService.buscarNotasPorIdAluno(idAluno);
            List<ResponseNotaDTO> responseNotaDTO = notaService.criarResponseNotaDTO(notaEntityList);
            log.info("GET /notas/alunos/{} ---> Sucesso.", idAluno);
            return ResponseEntity.status(HttpStatus.OK).body(responseNotaDTO);
        } catch (NotaNotFoundException | AlunoNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

