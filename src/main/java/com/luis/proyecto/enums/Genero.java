package com.luis.proyecto.enums;


import com.luis.proyecto.exceptions.RecursoNoEncontradoException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Genero {
    MASCULINO("M", "Masculino"),
    FEMENINO("F", "Femenino");

    private final String abreviacion;
    private final String descripcion;

    public static Genero obtenerPorAbreviacion(Character letra) {
        if(letra== null)
            throw new IllegalArgumentException("El caracter no puede ser nulo");
        for (Genero genero : Genero.values()) {
            if (genero.abreviacion.equalsIgnoreCase(letra.toString())) {
                return genero;
            }
        }
        throw new RecursoNoEncontradoException("No existe un genero para el caracter ingresado");
    }
}
