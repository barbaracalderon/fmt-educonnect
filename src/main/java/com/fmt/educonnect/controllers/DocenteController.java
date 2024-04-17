package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestDocenteDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseDocenteDTO;
import com.fmt.educonnect.datasource.entities.DocenteEntity;
import com.fmt.educonnect.infra.exceptions.DocenteNotFoundException;
import com.fmt.educonnect.infra.exceptions.CadastroNotFoundException;
import com.fmt.educonnect.services.DocenteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("docentes")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @PostMapping
    public ResponseEntity<?> criarDocente(@Valid @RequestBody RequestDocenteDTO requestDocenteDTO) {
        try {
            log.info("POST /docentes ---> Chamada para o método.");
            DocenteEntity docenteEntitySalvo = docenteService.criarDocente(requestDocenteDTO);
            ResponseDocenteDTO responseDocenteDTO = docenteService.criarResponseDocenteDTO(docenteEntitySalvo);
            log.info("POST /docentes ---> Sucesso.");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDocenteDTO);
        } catch (CadastroNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<ResponseDocenteDTO>> listarDocentes() {
        log.info("GET /docentes ---> Chamada para o método.");
        List<DocenteEntity> docenteEntityList = docenteService.listarDocentes();
        if (docenteEntityList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            List<ResponseDocenteDTO> responseDocenteDTOsList = docenteService.criarResponseDocenteDTO(docenteEntityList);
            log.info("POST /docentes ---> Sucesso.");
            return ResponseEntity.ok().body(responseDocenteDTOsList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarDocentePorId(@PathVariable("id") Long id) {
        try {
            log.info("GET /docentes/{} ---> Chamada para o método.", id);
            DocenteEntity docenteEntitySalvo = docenteService.buscarDocentePorId(id);
            ResponseDocenteDTO responseDocenteDTO = docenteService.criarResponseDocenteDTO(docenteEntitySalvo);
            log.info("GET /docentes/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseDocenteDTO);
        } catch (DocenteNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarDocente(@PathVariable("id") Long id, @Valid @RequestBody RequestDocenteDTO requestDocenteDTO) {
        try {
            log.info("PUT /docentes/{} ---> Chamada para o método.", id);
            DocenteEntity docenteEntitySalvo = docenteService.atualizarDocente(id, requestDocenteDTO);
            ResponseDocenteDTO responseDocenteDTO = docenteService.criarResponseDocenteDTO(docenteEntitySalvo);
            log.info("PUT /docentes/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseDocenteDTO);
        } catch (DocenteNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarDocente(@PathVariable("id") Long id) {
        try {
            log.info("DELETE /docentes/{} ---> Chamada para o método.", id);
            docenteService.deletarDocente(id);
            log.info("DELETE /docentes/{} ---> Sucesso.", id);
            return ResponseEntity.noContent().build();
        } catch (DocenteNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }





}
