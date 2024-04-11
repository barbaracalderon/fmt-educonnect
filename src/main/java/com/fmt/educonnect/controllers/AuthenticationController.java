package com.fmt.educonnect.controllers;

import com.fmt.educonnect.interfaces.dtos.AuthenticationDTO;
import com.fmt.educonnect.interfaces.dtos.RegisterDTO;
import com.fmt.educonnect.models.UserModel;
import com.fmt.educonnect.repositories.UserRepository;
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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO body) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(body.login(), body.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();

    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO body) {
        if (this.userRepository.findByLogin(body.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(body.password());
        UserModel newUser = new UserModel(body.login(), encryptedPassword, body.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }


}
