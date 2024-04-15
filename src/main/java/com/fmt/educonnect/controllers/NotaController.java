package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestNotaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseNotaDTO;
import com.fmt.educonnect.infra.exceptions.*;
import com.fmt.educonnect.services.CursoService;
import com.fmt.educonnect.services.DocenteService;
import com.fmt.educonnect.services.NotaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notas")
public class NotaController {

    private final NotaService notaService;
    private CursoService cursoService;
    private DocenteService docenteService;

    @Autowired
    public NotaController(NotaService notaService, CursoService cursoService, DocenteService docenteService) {
        this.notaService = notaService;
        this.cursoService = cursoService;
        this.docenteService = docenteService;
    }

    @PostMapping()
    public ResponseEntity<?> criarNota(@RequestBody @Valid RequestNotaDTO requestNotaDTO) {

        try {
            ResponseNotaDTO responseNotaDTO = notaService.criarNota(requestNotaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseNotaDTO);
        } catch (AlunoNotFoundException | DocenteNotFoundException | MateriaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping()
    public ResponseEntity<List<ResponseNotaDTO>> listarNotas() {
        List<ResponseNotaDTO> ResponseNotaDTOsList = notaService.listarNotas();
        if (ResponseNotaDTOsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(ResponseNotaDTOsList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarNotaPorId(@PathVariable("id") Long id) {
        try {
            ResponseNotaDTO responseNotaDTO = notaService.buscarNotaPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseNotaDTO);
        } catch (NotaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarNota(@PathVariable("id") Long id, @RequestBody RequestNotaDTO requestNotaDTO) {
        try {
            ResponseNotaDTO responseNotaDTO = notaService.atualizarNota(id, requestNotaDTO);
            return ResponseEntity.status(HttpStatus.OK).body(responseNotaDTO);
        } catch (NotaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarNota(@PathVariable("id") Long id) {
        try {
            notaService.deletarNota(id);
            return ResponseEntity.noContent().build();
        } catch (NotaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

