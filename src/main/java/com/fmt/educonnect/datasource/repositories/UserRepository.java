package com.fmt.educonnect.datasource.repositories;

import com.fmt.educonnect.datasource.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserDetails findByLogin(String login);

}
