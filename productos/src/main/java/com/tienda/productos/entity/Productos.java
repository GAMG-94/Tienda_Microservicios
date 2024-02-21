package com.tienda.productos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Este campo es obligatorio")
    private String nombre;
    @NotNull(message = "Este campo es obligatorio")
    private String empresa;
    @NotNull(message = "Este campo es obligatorio")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fecha_pedido")
    private Date fechaPedido;
    @NotNull(message = "Este campo es obligatorio")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_entrega")
    private Date fechaEntrega;
    @NotNull(message = "Este campo es obligatorio")
    private Double precio;
    @NotNull(message = "Este campo es obligatorio")
    private Integer cantidad;
    @CreatedDate
    @Column(name = "fecha_registro", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date fechaRegistro;
}
