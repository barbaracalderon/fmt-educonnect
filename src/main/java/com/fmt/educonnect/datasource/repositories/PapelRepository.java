package com.fmt.educonnect.datasource.repositories;

import com.fmt.educonnect.datasource.entities.PapelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PapelRepository extends JpaRepository<PapelEntity, Long> {
}
