package com.fmt.educonnect.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocenteDTO {
    private int id;
    private String nome;
    private LocalDate dataEntrada;
    private int idUsuario;
}
