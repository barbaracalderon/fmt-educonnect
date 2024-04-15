package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestAlunoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseAlunoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseListaDeNotasAlunoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseNotaDTO;
import com.fmt.educonnect.infra.exceptions.*;
import com.fmt.educonnect.services.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping
    public ResponseEntity<?> criarAluno(@RequestBody RequestAlunoDTO requestAlunoDTO) {
        try {
            ResponseAlunoDTO responseAlunoDTO = alunoService.criarAluno(requestAlunoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseAlunoDTO);
        } catch (CadastroNotFoundException | TurmaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<ResponseAlunoDTO>> listarAlunos() {
        List<ResponseAlunoDTO> ResponseAlunoDTOsList = alunoService.listarAlunos();
        if (ResponseAlunoDTOsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(ResponseAlunoDTOsList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarAlunoPorId(@PathVariable("id") Long id) {
        try {
            ResponseAlunoDTO ResponseAlunoDTO = alunoService.buscarAlunoPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseAlunoDTO);
        } catch (AlunoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAluno(@PathVariable("id") Long id, @RequestBody RequestAlunoDTO RequestAlunoDTO) {
        try {
            ResponseAlunoDTO ResponseAlunoDTO = alunoService.atualizarAluno(id, RequestAlunoDTO);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseAlunoDTO);
        } catch (DocenteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAluno(@PathVariable("id") Long id) {
        try {
            alunoService.deletarAluno(id);
            return ResponseEntity.noContent().build();
        } catch (DocenteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/notas")
    public ResponseEntity<?> buscarNotasDeAlunoId(@PathVariable("id") Long id) {
        try {
            ResponseAlunoDTO responseAlunoDTO = alunoService.buscarAlunoPorId(id);
            ResponseListaDeNotasAlunoDTO responseListaDeNotasAlunoDTO = alunoService.buscarNotasDeAluno(responseAlunoDTO);
            return ResponseEntity.status(HttpStatus.OK).body(responseListaDeNotasAlunoDTO);
        } catch (NotaNotFoundException | AlunoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
