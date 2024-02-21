package com.tienda.vendedor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Este campo es obligatorio")
    private String nombre;
    @NotNull(message = "Este campo es obligatorio")
    private String apellido;
    @NotNull(message = "Este campo es obligatorio")
    private String correo;
    @NotNull(message = "Este campo es obligatorio")
    private String telefono;
    @NotNull(message = "Este campo es obligatorio")
    private String direccion;
    @NotNull(message = "Este campo es obligatorio")
    private String cargo;
    @NotNull(message = "Este campo es obligatorio")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @NotNull(message = "Este campo es obligatorio")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Temporal(TemporalType.DATE)
    private Date fechaSalida;
    @NotNull(message = "Este campo es obligatorio")
    private Double comision;
    private boolean activo;
    @CreatedDate
    @Column(name = "fecha_registro", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private java.util.Date fechaRegistro;
}
