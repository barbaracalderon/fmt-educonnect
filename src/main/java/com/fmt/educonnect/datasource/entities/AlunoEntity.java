package com.fmt.educonnect.datasource.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "aluno")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private Long idCadastro;

    @Column(name = "id_turma")
    private Long idTurma;

    @ManyToOne
    @JoinColumn(name = "id_turma", referencedColumnName = "id", insertable = false, updatable = false)
    private TurmaEntity turmaEntity;

    @ManyToOne
    @JoinColumn(name = "id_nota", referencedColumnName = "id", insertable = false, updatable = false)
    private NotaEntity notaEntity;

    public AlunoEntity(String nome, LocalDate dataNascimento, Long idCadastro, Long idTurma) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.idCadastro = idCadastro;
        this.idTurma = idTurma;
    }

}
