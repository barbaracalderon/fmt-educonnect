package com.fmt.educonnect.datasource.entities;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "materia")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class MateriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate dataEntrada;

    @Column(name = "id_curso")
    private Long idCurso;

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id", insertable = false, updatable = false)
    private CursoEntity cursoEntity;

    public MateriaEntity(String nome, LocalDate dataEntrada, Long idCurso) {
        this.nome = nome;
        this.dataEntrada = dataEntrada;
        this.idCurso = idCurso;
    }

}
