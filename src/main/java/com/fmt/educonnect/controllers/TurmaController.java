package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestTurmaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseTurmaDTO;
import com.fmt.educonnect.infra.exceptions.CursoNotFoundException;
import com.fmt.educonnect.infra.exceptions.TurmaNotFoundException;
import com.fmt.educonnect.services.TurmaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("turmas")
public class TurmaController {

    private final TurmaService turmaService;

    @Autowired
    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @PostMapping()
    public ResponseEntity<?> criarTurma(@RequestBody @Valid RequestTurmaDTO requestTurmaDTO) {

        try {
            log.info("POST /turmas ---> Chamada para o método.");
            ResponseTurmaDTO responseTurmaDTO = turmaService.criarTurma(requestTurmaDTO);
            log.info("POST /turmas ---> Sucesso.");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseTurmaDTO);
        } catch (CursoNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<ResponseTurmaDTO>> listarTurmas() {
        log.info("GET /turmas ---> Chamada para o método.");
        List<ResponseTurmaDTO> ResponseTurmaDTOsList = turmaService.listarTurmas();
        if (ResponseTurmaDTOsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("GET /turmas ---> Sucesso.");
            return ResponseEntity.ok().body(ResponseTurmaDTOsList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTurmaPorId(@PathVariable("id") Long id) {
        try {
            log.info("GET /turmas/{} ---> Chamada para o método.", id);
            ResponseTurmaDTO responseTurmaDTO = turmaService.buscarTurmaPorId(id);
            log.info("GET /turmas/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseTurmaDTO);
        } catch (TurmaNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarTurma(@PathVariable("id") Long id, @RequestBody RequestTurmaDTO requestTurmaDTO) {
        try {
            log.info("PUT /turmas/{} ---> Chamada para o método.", id);
            ResponseTurmaDTO responseTurmaDTO = turmaService.atualizarTurma(id, requestTurmaDTO);
            log.info("PUT /turmas/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseTurmaDTO);
        } catch (TurmaNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarTurma(@PathVariable("id") Long id) {
        try {
            log.info("DELETE /turmas/{} ---> Chamada para o método.", id);
            turmaService.deletarTurma(id);
            log.info("DELETE /turmas/{} ---> Sucesso.", id);
            return ResponseEntity.noContent().build();
        } catch (TurmaNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

