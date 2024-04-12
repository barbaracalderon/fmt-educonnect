package com.fmt.educonnect.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()

                        .requestMatchers(HttpMethod.POST, "/cadastro").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/cadastro").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/cadastro").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/cadastro").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/cadastro/{id}").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.POST, "/docentes").hasAnyRole("ADMIN", "PEDAGOGICO", "RECRUITER")
                        .requestMatchers(HttpMethod.GET, "/docentes").hasAnyRole("ADMIN", "PEDAGOGICO", "RECRUITER")
                        .requestMatchers(HttpMethod.PUT, "/docentes").hasAnyRole("ADMIN", "PEDAGOGICO", "RECRUITER")
                        .requestMatchers(HttpMethod.DELETE, "/docentes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/docentes/{id}").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.POST, "/turmas").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.GET, "/turmas").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.PUT, "/turmas").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.DELETE, "/turmas").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/turmas/{id}").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.POST, "/alunos").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.GET, "/alunos").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.PUT, "/alunos").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.DELETE, "/alunos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/alunos/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/alunos/pessoal").hasAnyRole("ADMIN", "PEDAGOGICO", "ALUNO")
                        .requestMatchers(HttpMethod.GET, "/alunos/pessoal/pontuacao").hasAnyRole("ADMIN", "PEDAGOGICO")


                        .requestMatchers(HttpMethod.POST, "/cursos").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.GET, "/cursos").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.PUT, "/cursos").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.DELETE, "/cursos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/cursos/{id}").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.POST, "/materias").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/materias").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/materias").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/materias").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/materias/{id}").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.POST, "/notas").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.GET, "/notas").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.PUT, "/notas").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.DELETE, "/notas").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/notas/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/notas/pessoal").hasAnyRole("ADMIN", "PEDAGOGICO", "ALUNO")


                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
