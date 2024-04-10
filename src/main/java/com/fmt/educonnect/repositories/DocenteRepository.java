package com.fmt.educonnect.repositories;

import com.fmt.educonnect.models.DocenteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends JpaRepository<DocenteModel, Integer> {
}
