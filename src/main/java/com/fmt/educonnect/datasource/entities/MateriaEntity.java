package com.fmt.educonnect.datasource.entities;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "materia")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class MateriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate dataEntrada;

    @Column(name = "id_curso", insertable = false, updatable = false)
    private Long idCurso;

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    private CursoEntity cursoEntity;

    public MateriaEntity(String nome, LocalDate dataEntrada, Long idCurso, CursoEntity cursoEntity) {
        this.nome = nome;
        this.dataEntrada = dataEntrada;
        this.idCurso = idCurso;
    }


    public void setNome(String nome) {
    }

    public void setDataEntrada(LocalDate localDate) {
    }
}
