package com.tienda.proveedores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProveedoresDTO {
    private Long id;
    private String nombreEmpresa;
    private String nombreEmpleado;
    private String direccion;
    private String correo;
    private String telefono;
    private String sitioWeb;
    private String atencionCliente;
    private String redesSociales;
    private String contactoVentas;
}
