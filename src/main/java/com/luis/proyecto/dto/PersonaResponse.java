package com.luis.proyecto.dto;

public record PersonaResponse(
        Long id,
        String nombre,
        Short edad,
        String genero,
        String email,
        String telefono
) {
}
