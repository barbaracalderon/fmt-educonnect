package com.fmt.educonnect.datasource.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "turma")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TurmaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate dataEntrada;

    @Column(name = "id_docente")
    private Long idDocente;

    @ManyToOne
    @JoinColumn(name = "id_docente", referencedColumnName = "id", insertable = false, updatable = false)
    private DocenteEntity docenteEntity;

    @Column(name = "id_curso")
    private Long idCurso;

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id", insertable = false, updatable = false)
    private CursoEntity cursoEntity;

}
