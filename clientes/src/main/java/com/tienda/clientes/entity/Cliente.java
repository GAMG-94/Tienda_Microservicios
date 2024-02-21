package com.tienda.clientes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Este campo es obligatorio")
    private String nombre;
    @NotNull(message = "Este campo es obligatorio")
    private String correo;
    @NotNull(message = "Este campo es obligatorio")
    @Size(min = 1, max = 10)
    private String telefono;
    @CreatedDate
    @Column(name = "fecha_registro", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date fechaRegistro;
}
