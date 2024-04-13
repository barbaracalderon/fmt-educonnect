package com.fmt.educonnect.datasource.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity(name = "cadastro")
@Table(name = "cadastro")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CadastroEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String login;
    private String password;

    @Column(name = "id_papel")
    private Long idPapel;

    @OneToOne
    @JoinColumn(name = "id_papel", referencedColumnName = "id", insertable = false, updatable = false)
    private PapelEntity papelEntity;

    public CadastroEntity(String nome, String login, String encryptedPassword, Long idPapel) {
        this.nome = nome;
        this.login = login;
        this.password = encryptedPassword;
        this.idPapel = idPapel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (papelEntity != null && papelEntity.getNomePapel().equals(UserRoleEnum.ADMIN.toString())) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}