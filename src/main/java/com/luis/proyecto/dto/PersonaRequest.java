package com.luis.proyecto.dto;

import jakarta.validation.constraints.*;

public record PersonaRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El apellido paterno es requerido")
        @Size(min = 2, max = 50, message = "El apellido paterno debe tener entre 2 y 50 caracteres")
        String apellidoPaterno,

        @NotBlank(message = "El apellido materno es requerido")
        @Size(min = 2, max = 50, message = "El apellido materno debe tener entre 2 y 50 caracteres")
        String apellidoMaterno,

        @NotNull(message = "La edad es requerida")
        @Positive(message = "La edad debe ser positiva")
        @Max(value = 100, message = "La edad maxima permitida es 100 años")
        Short edad,

        @NotNull(message = "El genero es requerido")
        Character genero,

        @NotNull(message = "El telefono es requerido")
        @Pattern(regexp = "^[0-9]{10}$", message = "El telefono debe contener exactamente 10 digitos")
        String telefono
) {

}
