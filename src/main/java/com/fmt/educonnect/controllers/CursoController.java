package com.fmt.educonnect.controllers;
import com.fmt.educonnect.controllers.dtos.requests.RequestCursoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCursoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCursoMateriasDTO;
import com.fmt.educonnect.infra.exceptions.CursoNotFoundException;
import com.fmt.educonnect.infra.exceptions.DocenteNotFoundException;
import com.fmt.educonnect.datasource.entities.CursoEntity;
import com.fmt.educonnect.infra.exceptions.MateriaNotFoundException;
import com.fmt.educonnect.services.CursoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<ResponseCursoDTO> criarCurso(@Valid @RequestBody RequestCursoDTO requestCursoDTO) {
        log.info("POST /cursos ---> Chamada para o método.");

        CursoEntity cursoEntitySalvo = cursoService.criarCurso(requestCursoDTO);
        ResponseCursoDTO responseCursoDTO = cursoService.criarResponseCursoDTO(cursoEntitySalvo);
        if (responseCursoDTO != null) {
            log.info("POST /cursos ---> Sucesso.");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseCursoDTO);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<ResponseCursoDTO>> listarCursos() {
        log.info("GET /cursos ---> Chamada para o método.");
        List<CursoEntity> cursoEntityList = cursoService.listarCursos();
        if (cursoEntityList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            List<ResponseCursoDTO> responseCursoDTOsList = cursoService.criarResponseCursoDTO(cursoEntityList);
            log.info("GET /cursos ---> Sucesso.");
            return ResponseEntity.ok().body(responseCursoDTOsList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCursoPorId(@PathVariable("id") Long id) {
        try {
            log.info("GET /cursos/{} ---> Chamada para o método.", id);
            CursoEntity cursoEntity = cursoService.buscarCursoPorId(id);
            ResponseCursoDTO responseCursoDTO = cursoService.criarResponseCursoDTO(cursoEntity);
            log.info("GET /cursos/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseCursoDTO);
        } catch (CursoNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCurso(@PathVariable("id") Long id, @Valid @RequestBody RequestCursoDTO requestCursoDTO) {
        try {
            log.info("PUT /cursos/{} ---> Chamada para o método.", id);
            CursoEntity cursoEntitySalvo = cursoService.atualizarCurso(id, requestCursoDTO);
            ResponseCursoDTO responseCursoDTO = cursoService.criarResponseCursoDTO(cursoEntitySalvo);
            log.info("PUT /cursos/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseCursoDTO);
        } catch (CursoNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarCurso(@PathVariable("id") Long id) {
        try {
            log.info("DELETE /cursos/{} ---> Chamada para o método.", id);
            cursoService.deletarCurso(id);
            log.info("DELETE /cursos/{} ---> Sucesso.", id);
            return ResponseEntity.noContent().build();
        } catch (CursoNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
