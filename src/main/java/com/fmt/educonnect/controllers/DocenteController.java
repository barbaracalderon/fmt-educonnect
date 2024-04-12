package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestDocenteDTO;
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
    public ResponseEntity<RequestDocenteDTO> criarDocente(@RequestBody RequestDocenteDTO body) {
        RequestDocenteDTO docenteCriado = docenteService.criarDocente(body);
        if (docenteCriado != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(docenteCriado);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<RequestDocenteDTO>> listarDocentes() {
        List<RequestDocenteDTO> docentes = docenteService.listarDocentes();
        if (!docentes.isEmpty()) {
            return ResponseEntity.ok(docentes);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
