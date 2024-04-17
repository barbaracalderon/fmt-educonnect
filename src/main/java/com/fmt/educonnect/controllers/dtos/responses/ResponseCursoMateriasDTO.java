package com.fmt.educonnect.controllers.dtos.responses;

import com.fmt.educonnect.datasource.entities.MateriaEntity;

import java.util.List;

public record ResponseCursoMateriasDTO (
    Long id,
    List<MateriaEntity> materias) {
}
