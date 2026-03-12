package com.luis.proyecto.entities;

import com.luis.proyecto.enums.Genero;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "PERSONAS")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERSONA")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", nullable = false, length = 50)
    private String apellidoMaterno;

    @Column(name = "EDAD", nullable = false)
    private Short edad;

    @Column(name = "GENERO", nullable = false)
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

    @Column(name = "TELEFONO", nullable = false, length = 10, unique = true)
    private String telefono;
}
