package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestCursoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCursoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCursoMateriasDTO;
import com.fmt.educonnect.infra.exceptions.DocenteNotFoundException;
import com.fmt.educonnect.services.CursoService;
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
    public ResponseEntity<ResponseCursoDTO> criarCurso(@RequestBody RequestCursoDTO requestCursoDTO) {
        log.info("POST /cursos ---> Chamada para o método.");
        ResponseCursoDTO responseCursoDTO = cursoService.criarCurso(requestCursoDTO);
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
        List<ResponseCursoDTO> responseCursoDTOsList = cursoService.listarCursos();
        if (responseCursoDTOsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("GET /cursos ---> Sucesso.");
            return ResponseEntity.ok().body(responseCursoDTOsList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCursoPorId(@PathVariable("id") Long id) {
        try {
            log.info("GET /cursos/{} ---> Chamada para o método.", id);
            ResponseCursoDTO responseCursoDTO = cursoService.buscarCursoPorId(id);
            log.info("GET /cursos/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseCursoDTO);
        } catch (DocenteNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCurso(@PathVariable("id") Long id, @RequestBody RequestCursoDTO requestCursoDTO) {
        try {
            log.info("PUT /cursos/{} ---> Chamada para o método.", id);
            ResponseCursoDTO responseCursoDTO = cursoService.atualizarCurso(id, requestCursoDTO);
            log.info("PUT /cursos/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseCursoDTO);
        } catch (DocenteNotFoundException e) {
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
        } catch (DocenteNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/materias")
    public ResponseEntity<?> buscarMateriasPorCurso(@PathVariable("id") Long id) {
        try {
            log.info("GET /cursos/{}/materias ---> Chamada para o método.", id);
            List<ResponseCursoMateriasDTO> responseCursoMateriasDTO = cursoService.buscarMateriasDeCursoId(id);
            log.info("GET /cursos/{}/materias ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseCursoMateriasDTO);
        } catch (DocenteNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
