package com.luis.proyecto.services;

import com.luis.proyecto.dto.PersonaRequest;
import com.luis.proyecto.dto.PersonaResponse;

import java.util.List;

public interface PersonaService {
    List<PersonaResponse> listar();
    List<PersonaResponse> obtenerPorNombre(String nombre);
    List<PersonaResponse> obtenerPorEmail(String email);
    List<PersonaResponse> obtenerPorRangoEdad(Short edadMin, Short edadMax);
    List<PersonaResponse> obtenerPorTelefono(String telefono);
    List<PersonaResponse> obtenerPorGenero(Character Genero);
    PersonaResponse obtenerPorId(Long id);
    PersonaResponse registrar(PersonaRequest request);
    PersonaResponse actualizar(PersonaRequest request, Long id);

    void eliminar(Long id);
}
