package com.fmt.educonnect.datasource.repositories;

import com.fmt.educonnect.datasource.entities.CadastroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface CadastroRepository extends JpaRepository<CadastroEntity, Long> {
    UserDetails findByLogin(String login);

}
