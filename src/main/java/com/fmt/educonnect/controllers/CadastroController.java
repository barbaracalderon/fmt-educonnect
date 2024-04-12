package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestCadastroDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseCadastroDTO;
import com.fmt.educonnect.datasource.entities.UserEntity;
import com.fmt.educonnect.datasource.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cadastro")
public class CadastroController {


    @Autowired
    private UserRepository userRepository;


    @PostMapping()
    public ResponseEntity<ResponseCadastroDTO> cadastrarUsuario(@RequestBody @Valid RequestCadastroDTO body) {
        if (this.userRepository.findByLogin(body.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(body.password());
        UserEntity newUser = new UserEntity(body.login(), encryptedPassword, body.role());
        UserEntity savedUser = this.userRepository.save(newUser);

        ResponseCadastroDTO responseCadastroDTO = new ResponseCadastroDTO(savedUser.getId(), savedUser.getLogin(), savedUser.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseCadastroDTO);
    }

    @GetMapping()
    public ResponseEntity<List<ResponseCadastroDTO>> listarUsuariosCadastrados() {
        List<UserEntity> users = userRepository.findAll();
        List<ResponseCadastroDTO> responseDTOs = users.stream()
                .map(user -> new ResponseCadastroDTO(user.getId(), user.getLogin(), user.getRole()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }


}
