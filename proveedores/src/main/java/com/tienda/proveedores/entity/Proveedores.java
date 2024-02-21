package com.tienda.proveedores.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table
@Entity
public class Proveedores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Este campo es obligatorio")
    private String nombreEmpresa;
    @NotNull(message = "Este campo es obligatorio")
    private String nombreEmpleado;
    @NotNull(message = "Este campo es obligatorio")
    private String direccion;
    @NotNull(message = "Este campo es obligatorio")
    private String correo;
    @NotNull(message = "Este campo es obligatorio")
    private String telefono;
    @NotNull(message = "Este campo es obligatorio")
    private String sitioWeb;
    @NotNull(message = "Este campo es obligatorio")
    private String atencionCliente;
    @NotNull(message = "Este campo es obligatorio")
    private String redesSociales;
    @NotNull(message = "Este campo es obligatorio")
    private String contactoVentas;
    @CreatedDate
    @Column(name = "fecha_registro", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date fechaRegistro;
}
