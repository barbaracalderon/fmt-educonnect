package com.fmt.educonnect.services;

import com.fmt.educonnect.datasource.repositories.CadastroRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    CadastroRepository cadastroRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return cadastroRepository.findByLogin(username);
    }

}
