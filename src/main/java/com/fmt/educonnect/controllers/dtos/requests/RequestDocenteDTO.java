package com.fmt.educonnect.controllers.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDocenteDTO {
    private int id;
    private String nome;
    private LocalDate dataEntrada;
    private int idUsuario;
}
