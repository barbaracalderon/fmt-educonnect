package com.fmt.educonnect.datasource.repositories;

import com.fmt.educonnect.datasource.entities.DocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends JpaRepository<DocenteEntity, Long> {
}
