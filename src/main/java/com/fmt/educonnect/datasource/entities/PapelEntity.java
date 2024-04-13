package com.fmt.educonnect.datasource.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "papel")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class PapelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomePapel;

    public PapelEntity(String nomePapel) {
        this.nomePapel = nomePapel;
    }
}
