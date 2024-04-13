package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestDocenteDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseDocenteDTO;
import com.fmt.educonnect.infra.exceptions.DocenteNotFoundException;
import com.fmt.educonnect.services.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("docentes")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @PostMapping
    public ResponseEntity<ResponseDocenteDTO> criarDocente(@RequestBody RequestDocenteDTO requestDocenteDTO) {
        ResponseDocenteDTO responseDocenteDTO = docenteService.criarDocente(requestDocenteDTO);
        if (responseDocenteDTO != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDocenteDTO);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<ResponseDocenteDTO>> listarDocentes() {
        List<ResponseDocenteDTO> responseDocenteDTOsList = docenteService.listarDocentes();
        if (responseDocenteDTOsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(responseDocenteDTOsList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarDocentePorId(@PathVariable("id") Long id) {
        try {
            ResponseDocenteDTO responseDocenteDTO = docenteService.buscarDocentePorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseDocenteDTO);
        } catch (DocenteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarDocente(@PathVariable("id") Long id, @RequestBody RequestDocenteDTO requestDocenteDTO) {
        try {
            ResponseDocenteDTO responseDocenteDTO = docenteService.atualizarDocente(id, requestDocenteDTO);
            return ResponseEntity.status(HttpStatus.OK).body(responseDocenteDTO);
        } catch (DocenteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarDocente(@PathVariable("id") Long id) {
        try {
            docenteService.deletarDocente(id);
            return ResponseEntity.noContent().build();
        } catch (DocenteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }





}
