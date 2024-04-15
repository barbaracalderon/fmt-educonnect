package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestCursoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCursoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCursoMateriasDTO;
import com.fmt.educonnect.infra.exceptions.DocenteNotFoundException;
import com.fmt.educonnect.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<ResponseCursoDTO> criarCurso(@RequestBody RequestCursoDTO requestCursoDTO) {
        ResponseCursoDTO responseCursoDTO = cursoService.criarCurso(requestCursoDTO);
        if (responseCursoDTO != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseCursoDTO);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<ResponseCursoDTO>> listarCursos() {
        List<ResponseCursoDTO> responseCursoDTOsList = cursoService.listarCursos();
        if (responseCursoDTOsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(responseCursoDTOsList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCursoPorId(@PathVariable("id") Long id) {
        try {
            ResponseCursoDTO responseCursoDTO = cursoService.buscarCursoPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseCursoDTO);
        } catch (DocenteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCurso(@PathVariable("id") Long id, @RequestBody RequestCursoDTO requestCursoDTO) {
        try {
            ResponseCursoDTO responseCursoDTO = cursoService.atualizarCurso(id, requestCursoDTO);
            return ResponseEntity.status(HttpStatus.OK).body(responseCursoDTO);
        } catch (DocenteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarCurso(@PathVariable("id") Long id) {
        try {
            cursoService.deletarCurso(id);
            return ResponseEntity.noContent().build();
        } catch (DocenteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/materias")
    public ResponseEntity<?> buscarMateriasPorCurso(@PathVariable("id") Long id) {
        try {

            List<ResponseCursoMateriasDTO> responseCursoMateriasDTO = cursoService.buscarMateriasDeCursoId(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseCursoMateriasDTO);
        } catch (DocenteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
