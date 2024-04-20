package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestAlunoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseAlunoDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseAlunoListaDeNotasDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseAlunoPontuacaoDTO;
import com.fmt.educonnect.datasource.entities.AlunoEntity;
import com.fmt.educonnect.datasource.entities.CadastroEntity;
import com.fmt.educonnect.datasource.entities.NotaEntity;
import com.fmt.educonnect.infra.exceptions.*;
import com.fmt.educonnect.services.AlunoNotaService;
import com.fmt.educonnect.services.AlunoService;
import com.fmt.educonnect.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.springframework.security.config.Elements.JWT;

@Slf4j
@RestController
@RequestMapping("alunos")
public class AlunoController {

    @Autowired
    private AlunoNotaService alunoNotaService;
    @Autowired AlunoService alunoService;

    @Autowired
    TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> criarAluno(@Valid  @RequestBody RequestAlunoDTO requestAlunoDTO) {
        try {
            log.info("POST /alunos ---> Chamada para o método.");
            AlunoEntity alunoEntity = alunoNotaService.criarAluno(requestAlunoDTO);
            ResponseAlunoDTO responseAlunoDTO = alunoService.criarResponseAlunoDTO(alunoEntity);
            log.info("POST /alunos ---> Sucesso.");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseAlunoDTO);
        } catch (CadastroNotFoundException | TurmaNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<ResponseAlunoDTO>> listarAlunos() {
        log.info("GET /alunos ---> Chamada para o método.");
        List<AlunoEntity> alunoEntityList = alunoService.listarAlunos();
        if (alunoEntityList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            List<ResponseAlunoDTO> responseAlunoDTOsList = alunoService.criarResponseAlunoDTO(alunoEntityList);
                    log.info("GET /alunos ---> Sucesso.");
            return ResponseEntity.ok().body(responseAlunoDTOsList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarAlunoPorId(@PathVariable("id") Long id) {
        try {
            log.info("GET /alunos/{} ---> Chamada para o método.", id);
            AlunoEntity alunoEntity = alunoService.buscarAlunoPorId(id);
            ResponseAlunoDTO ResponseAlunoDTO = alunoService.criarResponseAlunoDTO(alunoEntity);
                    log.info("GET /alunos/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseAlunoDTO);
        } catch (AlunoNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAluno(@PathVariable("id") Long id, @Valid @RequestBody RequestAlunoDTO RequestAlunoDTO) {
        try {
            log.info("PUT /alunos/{} ---> Chamada para o método.", id);
            AlunoEntity alunoEntity = alunoService.atualizarAluno(id, RequestAlunoDTO);
            ResponseAlunoDTO ResponseAlunoDTO = alunoService.criarResponseAlunoDTO(alunoEntity);
            log.info("PUT /alunos/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseAlunoDTO);
        } catch (AlunoNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAluno(@PathVariable("id") Long id) {
        try {
            log.info("DELETE /alunos/{} ---> Chamada para o método.", id);
            alunoService.deletarAluno(id);
            log.info("DELETE /alunos/{} ---> Sucesso.", id);
            return ResponseEntity.noContent().build();
        } catch (AlunoNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/notas")
    public ResponseEntity<?> buscarNotasDeAlunoId(@PathVariable("id") Long id, HttpServletRequest request) {
        try {
            log.info("GET /alunos/{}/notas ---> Chamada para o método.", id);
            log.info("GET /alunos/{}/notas ---> Validando token e userId.", id);

            String token = tokenService.extractToken(request);
            String loginCadastro = tokenService.validateToken(token);
            CadastroEntity cadastroEntity = alunoNotaService.buscarCadastroPorLogin(loginCadastro);

            try {
                AlunoEntity alunoEntity = alunoService.buscarAlunoPorId(id);

                if (hasAccessPermission(cadastroEntity, alunoEntity)) {
                    try {
                        List<NotaEntity> notasEntityList = alunoNotaService.buscarNotasPorIdAluno(alunoEntity);
                        ResponseAlunoListaDeNotasDTO responseAlunoListaDeNotasDTO = alunoService.criarResponseAlunoListaDeNotasDTO(notasEntityList);
                        log.info("GET /alunos/{}/notas ---> Sucesso.", id);
                        return ResponseEntity.status(HttpStatus.OK).body(responseAlunoListaDeNotasDTO);
                    } catch (NotaNotFoundException e) {
                        log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
                    }


                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso não autorizado para este id: " + id);
                }
            } catch (AlunoNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso não autorizado para este id: " + id);
        }
    }


    @GetMapping("/{id}/pontuacao")
    public ResponseEntity<?> buscarPontuacaoDeAlunoId(@PathVariable("id") Long id, HttpServletRequest request) {
        try {
            log.info("GET /alunos/{}/pontuação ---> Chamada para o método.", id);
            log.info("GET /alunos/{}/notas ---> Validando token e userId.", id);

            String token = tokenService.extractToken(request);
            String loginCadastro = tokenService.validateToken(token);
            CadastroEntity cadastroEntity = alunoNotaService.buscarCadastroPorLogin(loginCadastro);

            try {
                AlunoEntity alunoEntity = alunoService.buscarAlunoPorId(id);

                if (hasAccessPermission(cadastroEntity, alunoEntity)) {
                    try {
                        List<NotaEntity> notaEntityList = alunoNotaService.buscarNotasPorIdAluno(alunoEntity);
                        Long pontuacao = alunoService.calcularPontuacaoDeAluno(notaEntityList);
                        ResponseAlunoPontuacaoDTO responseAlunoPontuacaoDTO = alunoService.criarResponseAlunoPontuacaoDTO(alunoEntity, pontuacao);
                        log.info("GET /alunos/{}/pontuação ---> Sucesso.", id);
                        return ResponseEntity.status(HttpStatus.OK).body(responseAlunoPontuacaoDTO);
                    } catch (NotaNotFoundException e) {
                        log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso não autorizado para este id: " + id);
                }
            } catch (AlunoNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso não autorizado para este id: " + id);
        }
    }

    private boolean hasAccessPermission(CadastroEntity cadastroEntity, AlunoEntity alunoEntity) {
        return cadastroEntity.getIdPapel() == 1 || alunoEntity.getIdCadastro().equals(cadastroEntity.getId());
    }



}
