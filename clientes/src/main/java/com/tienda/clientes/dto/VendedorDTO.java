package com.tienda.clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VendedorDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String direccion;
    private String cargo;
    private Date fechaIngreso;
    private Date fechaSalida;
    private Double comision;
    private boolean activo;
}
