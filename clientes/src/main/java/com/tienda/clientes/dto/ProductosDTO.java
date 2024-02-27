package com.tienda.clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductosDTO {
    private Long id;
    private String nombre;
    private String empresa;
    private Date fechaPedido;
    private Date fechaEntrega;
    private Double precio;
    private Integer cantidad;
}
