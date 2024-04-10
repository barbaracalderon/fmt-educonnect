package com.fmt.educonnect.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocenteModel {
    private int id;
    private String nome;
    private LocalDate dataEntrada;
    private int idUsuario;
}

