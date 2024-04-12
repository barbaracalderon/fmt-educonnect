package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestLoginDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseLoginDTO;
import com.fmt.educonnect.controllers.dtos.requests.RequestCadastroDTO;
import com.fmt.educonnect.datasource.entities.UserEntity;
import com.fmt.educonnect.datasource.repositories.UserRepository;
import com.fmt.educonnect.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cadastro")
public class CadastroController {


    @Autowired
    private UserRepository userRepository;

    @Autowired


    @PostMapping()
    public ResponseEntity<Void> cadastrarUsuario(@RequestBody @Valid RequestCadastroDTO body) {
        if (this.userRepository.findByLogin(body.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(body.password());
        UserEntity newUser = new UserEntity(body.login(), encryptedPassword, body.role());

        this.userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }


}
