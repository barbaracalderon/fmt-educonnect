package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestMateriaDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseMateriaDTO;
import com.fmt.educonnect.datasource.entities.MateriaEntity;
import com.fmt.educonnect.infra.exceptions.CursoNotFoundException;
import com.fmt.educonnect.infra.exceptions.MateriaNotFoundException;
import com.fmt.educonnect.services.CursoService;
import com.fmt.educonnect.services.MateriaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("materias")
public class MateriaController {

    private final MateriaService materiaService;

    @Autowired
    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @PostMapping()
    public ResponseEntity<?> criarMateria(@Valid @RequestBody RequestMateriaDTO requestMateriaDTO) {

        try {
            log.info("POST /materias ---> Chamada para o método.");

            MateriaEntity materiaEntitySalvo = materiaService.criarMateria(requestMateriaDTO);
            ResponseMateriaDTO responseMateriaDTO = materiaService.criarResponseMateriaDTO(materiaEntitySalvo);
            log.info("POST /materias ---> Sucesso.");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMateriaDTO);
        } catch (MateriaNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<ResponseMateriaDTO>> listarMaterias() {
        log.info("GET /materias ---> Chamada para o método.");
        List<MateriaEntity> materiaEntityList = materiaService.listarMaterias();
        if (materiaEntityList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            List<ResponseMateriaDTO> ResponseMateriaDTOsList = materiaService.criarResponseMateriaDTO(materiaEntityList);
                    log.info("GET /materias ---> Sucesso.");
            return ResponseEntity.ok().body(ResponseMateriaDTOsList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarMateriaPorId(@PathVariable("id") Long id) {
        try {
            log.info("GET /materias/{} ---> Chamada para o método.", id);
            MateriaEntity materiaEntity = materiaService.buscarMateriaPorId(id);
            ResponseMateriaDTO responseMateriaDTO = materiaService.criarResponseMateriaDTO(materiaEntity);
            log.info("GET /materias/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseMateriaDTO);
        } catch (MateriaNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarMateria(@PathVariable("id") Long id, @Valid @RequestBody RequestMateriaDTO requestMateriaDTO) {
        try {
            log.info("PUT /materias/{} ---> Chamada para o método.", id);
            MateriaEntity materiaEntitySalvo = materiaService.atualizarMateria(id, requestMateriaDTO);
            ResponseMateriaDTO responseMateriaDTO = materiaService.criarResponseMateriaDTO(materiaEntitySalvo);
                    log.info("PUT /materias/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseMateriaDTO);
        } catch (MateriaNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarMateria(@PathVariable("id") Long id) {
        try {
            log.info("DELETE /materias/{} ---> Chamada para o método.", id);
            materiaService.deletarMateria(id);
            log.info("DELETE /materias/{} ---> Sucesso.", id);
            return ResponseEntity.noContent().build();
        } catch (MateriaNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/cursos/{id_curso}")
    public ResponseEntity<?> listarMateriasPorCurso(@PathVariable("id_curso") Long idCurso) {
        log.info("GET /materias/cursos/{} ---> Chamada para o método.", idCurso);
        List<MateriaEntity> materiaEntityList = materiaService.listarMateriasPorCurso(idCurso);
        if (materiaEntityList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<ResponseMateriaDTO> responseMateriaDTO = materiaService.criarResponseMateriaDTO(materiaEntityList);
            log.info("GET /materias/cursos/{} ---> Sucesso.", idCurso);
            return new ResponseEntity<>(materiaEntityList, HttpStatus.OK);
        }
    }
}

