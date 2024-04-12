package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.AuthenticationDTO;
import com.fmt.educonnect.controllers.dtos.LoginResponseDTO;
import com.fmt.educonnect.controllers.dtos.RegisterDTO;
import com.fmt.educonnect.datasource.entities.UserEntity;
import com.fmt.educonnect.datasource.repositories.UserRepository;
import com.fmt.educonnect.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO body) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(body.login(), body.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO body) {
        if (this.userRepository.findByLogin(body.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(body.password());
        UserEntity newUser = new UserEntity(body.login(), encryptedPassword, body.role());

        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }


}
