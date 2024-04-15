package com.fmt.educonnect.datasource.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "nota")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class NotaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataLancamento;
    private Long valor;

    @Column(name = "id_aluno")
    private Long idAluno;

    @ManyToOne
    @JoinColumn(name = "id_aluno", referencedColumnName = "id", insertable = false, updatable = false)
    private AlunoEntity alunoEntity;

    @Column(name = "id_docente")
    private Long idDocente;

    @ManyToOne
    @JoinColumn(name = "id_docente", referencedColumnName = "id", insertable = false, updatable = false)
    private DocenteEntity docenteEntity;

    @Column(name = "id_materia")
    private Long idMateria;

    @ManyToOne
    @JoinColumn(name = "id_materia", referencedColumnName = "id", insertable = false, updatable = false)
    private MateriaEntity materiaEntity;


}
