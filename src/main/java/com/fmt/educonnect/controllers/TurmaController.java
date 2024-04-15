package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestTurmaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCursoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseTurmaDTO;
import com.fmt.educonnect.datasource.entities.CursoEntity;
import com.fmt.educonnect.infra.exceptions.CursoNotFoundException;
import com.fmt.educonnect.infra.exceptions.TurmaNotFoundException;
import com.fmt.educonnect.infra.exceptions.TurmaNotFoundException;
import com.fmt.educonnect.services.CursoService;
import com.fmt.educonnect.services.DocenteService;
import com.fmt.educonnect.services.TurmaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("turmas")
public class TurmaController {

    private TurmaService turmaService;
    private CursoService cursoService;
    private DocenteService docenteService;

    @Autowired
    public TurmaController(TurmaService turmaService, CursoService cursoService, DocenteService docenteService) {
        this.turmaService = turmaService;
        this.cursoService = cursoService;
        this.docenteService = docenteService;
    }

    @PostMapping()
    public ResponseEntity<?> criarTurma(@RequestBody @Valid RequestTurmaDTO requestTurmaDTO) {

        try {
            ResponseTurmaDTO responseTurmaDTO = turmaService.criarTurma(requestTurmaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseTurmaDTO);
        } catch (CursoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<ResponseTurmaDTO>> listarTurmas() {
        List<ResponseTurmaDTO> ResponseTurmaDTOsList = turmaService.listarTurmas();
        if (ResponseTurmaDTOsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(ResponseTurmaDTOsList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTurmaPorId(@PathVariable("id") Long id) {
        try {
            ResponseTurmaDTO responseTurmaDTO = turmaService.buscarTurmaPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseTurmaDTO);
        } catch (TurmaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarTurma(@PathVariable("id") Long id, @RequestBody RequestTurmaDTO requestTurmaDTO) {
        try {
            ResponseTurmaDTO responseTurmaDTO = turmaService.atualizarTurma(id, requestTurmaDTO);
            return ResponseEntity.status(HttpStatus.OK).body(responseTurmaDTO);
        } catch (TurmaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarTurma(@PathVariable("id") Long id) {
        try {
            turmaService.deletarTurma(id);
            return ResponseEntity.noContent().build();
        } catch (TurmaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

