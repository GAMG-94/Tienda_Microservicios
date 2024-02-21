package com.tienda.clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClienteDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
}
