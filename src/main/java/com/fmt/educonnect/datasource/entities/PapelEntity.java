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

    @Enumerated(EnumType.STRING)
    private NomePapelEnum nomePapel;

    public PapelEntity(String nomePapel) {
        if (!isValidNomePapel(nomePapel)) {
            throw new IllegalArgumentException(("Nome de papel inválido: " + nomePapel));
        }
        this.nomePapel = NomePapelEnum.valueOf(nomePapel);
    }

    private boolean isValidNomePapel(String nomePapel) {
            try{
                NomePapelEnum.valueOf(nomePapel);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    }
