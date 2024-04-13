package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestMateriaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseMateriaDTO;
import com.fmt.educonnect.infra.exceptions.DocenteNotFoundException;
import com.fmt.educonnect.infra.exceptions.MateriaNotFoundException;
import com.fmt.educonnect.services.CursoService;
import com.fmt.educonnect.services.MateriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("materias")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<ResponseMateriaDTO> criarMateria(@RequestBody @Valid RequestMateriaDTO requestMateriaDTO) {
        if (cursoService.buscarCursoPorId(requestMateriaDTO.idCurso()) != null) {

        }
        ResponseMateriaDTO responseMateriaDTO = materiaService.criarMateria(requestMateriaDTO);
        if (responseMateriaDTO != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMateriaDTO);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<ResponseMateriaDTO>> listarMaterias() {
        List<ResponseMateriaDTO> ResponseMateriaDTOsList = materiaService.listarMaterias();
        if (ResponseMateriaDTOsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(ResponseMateriaDTOsList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarMateriaPorId(@PathVariable("id") Long id) {
        try {
            ResponseMateriaDTO ResponseMateriaDTO = materiaService.buscarMateriaPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseMateriaDTO);
        } catch (DocenteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarMateria(@PathVariable("id") Long id, @RequestBody RequestMateriaDTO RequestMateriaDTO) {
        try {
            ResponseMateriaDTO ResponseMateriaDTO = materiaService.atualizarMateria(id, RequestMateriaDTO);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseMateriaDTO);
        } catch (MateriaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarMateria(@PathVariable("id") Long id) {
        try {
            materiaService.deletarMateria(id);
            return ResponseEntity.noContent().build();
        } catch (DocenteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
