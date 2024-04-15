package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestMateriaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseMateriaDTO;
import com.fmt.educonnect.infra.exceptions.CursoNotFoundException;
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

    private final MateriaService materiaService;

    @Autowired
    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @PostMapping()
    public ResponseEntity<?> criarMateria(@RequestBody @Valid RequestMateriaDTO requestMateriaDTO) {

        try {
            ResponseMateriaDTO responseMateriaDTO = materiaService.criarMateria(requestMateriaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMateriaDTO);
        } catch (CursoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
            ResponseMateriaDTO responseMateriaDTO = materiaService.buscarMateriaPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseMateriaDTO);
        } catch (MateriaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarMateria(@PathVariable("id") Long id, @RequestBody RequestMateriaDTO requestMateriaDTO) {
        try {
            ResponseMateriaDTO responseMateriaDTO = materiaService.atualizarMateria(id, requestMateriaDTO);
            return ResponseEntity.status(HttpStatus.OK).body(responseMateriaDTO);
        } catch (MateriaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarMateria(@PathVariable("id") Long id) {
        try {
            materiaService.deletarMateria(id);
            return ResponseEntity.noContent().build();
        } catch (MateriaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/cursos/{id_curso}")
    public ResponseEntity<?> listarMateriasPorCurso(@PathVariable("id_curso") Long idCurso) {
        List<ResponseMateriaDTO> materiaEntityList = materiaService.listarMateriasPorCurso(idCurso);

        if (materiaEntityList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(materiaEntityList, HttpStatus.OK);
        }
    }
}

