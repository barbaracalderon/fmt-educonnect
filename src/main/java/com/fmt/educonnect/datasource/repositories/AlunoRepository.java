package com.fmt.educonnect.datasource.repositories;

import com.fmt.educonnect.datasource.entities.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoEntity, Long> {
}
