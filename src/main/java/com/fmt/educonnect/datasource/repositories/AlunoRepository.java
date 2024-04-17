package com.fmt.educonnect.datasource.repositories;

import com.fmt.educonnect.datasource.entities.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoEntity, Long> {

    List<AlunoEntity> findAllByIdCadastro(Long id);

    List<AlunoEntity> findAllByIdTurma(Long id);
}
