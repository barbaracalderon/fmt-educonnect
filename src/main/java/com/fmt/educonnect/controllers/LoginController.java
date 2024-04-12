package com.fmt.educonnect.controllers;

import com.fmt.educonnect.controllers.dtos.requests.RequestLoginDTO;
import com.fmt.educonnect.controllers.dtos.responses.ResponseLoginDTO;
import com.fmt.educonnect.datasource.entities.UserEntity;
import com.fmt.educonnect.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid RequestLoginDTO body) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(body.login(), body.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        return ResponseEntity.ok(new ResponseLoginDTO(token));
    }

}
