package com.fmt.educonnect.datasource.repositories;

import com.fmt.educonnect.datasource.entities.TurmaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaRepository extends JpaRepository<TurmaEntity, Long> {
}
