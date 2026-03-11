package com.luis.proyecto.services;

import com.luis.proyecto.dto.PersonaRequest;
import com.luis.proyecto.dto.PersonaResponse;
import com.luis.proyecto.entities.Persona;
import com.luis.proyecto.enums.Genero;
import com.luis.proyecto.exceptions.RecursoNoEncontradoException;
import com.luis.proyecto.mappers.PersonaMapper;
import com.luis.proyecto.repositories.PersonaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository personaRepository;

    private final PersonaMapper personaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponse> listar() {
        log.info("listado de todas las personas solicitadas");
        return personaRepository.findAll().stream().map(personaMapper::entityToResponse).toList();
    }

    @Override
    public PersonaResponse obtenerPorId(Long id) {
        return personaMapper.entityToResponse(obtenerPersonaOException(id));
    }

    @Override
    public PersonaResponse registrar(PersonaRequest request) {
        log.info("registrando nueva persona {}", request.nombre());
        Genero genero = Genero.obtenerPorAbreviacion(request.genero());

        String email = generarEmail(request.nombre(), request.apellidoPaterno(), request.apellidoMaterno());

        Persona persona = personaRepository.save(personaMapper.requestToEntity(request, genero, email));
        log.info("Nueva persona registrada: {}", persona.getNombre());

        return personaMapper.entityToResponse(persona);
    }

    @Override
    public PersonaResponse actualizar(PersonaRequest request, Long id) {
        Persona persona = obtenerPersonaOException(id);

        log.info("actualizando persona con id: {}", id);
        persona.setNombre(request.nombre());
        persona.setApellidoPaterno(request.apellidoPaterno());
        persona.setApellidoMaterno(request.apellidoMaterno());
        persona.setEdad(request.edad());
        persona.setTelefono(request.telefono());

        Genero genero = Genero.obtenerPorAbreviacion(request.genero());
        persona.setGenero(genero);

        String email = generarEmail(request.nombre(), request.apellidoPaterno(), request.apellidoMaterno());
        persona.setEmail(email);

        log.info("Persona actualizada con id: {}", id);
        return personaMapper.entityToResponse(persona);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando persona con id: {}", id);
        personaRepository.delete(obtenerPersonaOException(id));
        log.info("Persona eliminada con id: {}", id);
    }

    public Persona obtenerPersonaOException(Long id) {
        log.info("Buscando Persona con id {}", id);
        return personaRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Persona no encontrada con el id: " + id));
    }

    private String obtenerPrimerosCaracteres(String texto, int cantidad) {
        if (texto == null) return "";
        return texto.length() <= cantidad ? texto : texto.substring(0, cantidad);
    }

    private String generarEmail(String nombre, String apellidoPaterno, String apellidoMaterno) {
        log.info("Generando email...");
        return (
                obtenerPrimerosCaracteres(nombre, 5) +
                        obtenerPrimerosCaracteres(apellidoPaterno, 5) +
                        obtenerPrimerosCaracteres(apellidoMaterno, 5) +
                        "@ejemplo.com"
                ).toLowerCase();
    }
}
