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
    @Transactional
    public List<PersonaResponse> obtenerPorNombre(String nombre) {
        log.info("Buscando personas que contengan el nombre: {}", nombre);
        return personaRepository.findByNombreContainingIgnoreCase(nombre).stream().map(personaMapper::entityToResponse).toList();
    }

    @Override
    public List<PersonaResponse> obtenerPorEmail(String email) {
        log.info("Buscando personas que contengan el email: {}", email);
        return personaRepository.findByEmailContaining(email.toLowerCase()).stream().map(personaMapper::entityToResponse).toList();
    }

    @Override
    public List<PersonaResponse> obtenerPorRangoEdad(Short edadMin, Short edadMax) {
        log.info("Buscando personas con edad entre {} {}", edadMin, edadMax);
        if (edadMin > edadMax) {
            throw new IllegalArgumentException("La edad inicial no puede ser mayor que la edad final");
        }
        return personaRepository.findByEdadBetween(edadMin, edadMax).stream().map(personaMapper::entityToResponse).toList();
    }

    @Override
    public List<PersonaResponse> obtenerPorTelefono(String telefono) {
        log.info("Buscando persona con telefono: {}", telefono);
        return personaRepository.findByTelefono(telefono).stream().map(personaMapper::entityToResponse).toList();
    }

    @Override
    public List<PersonaResponse> obtenerPorGenero(Character genero) {
        Genero generoEnum = Genero.obtenerPorAbreviacion(genero);
        log.info("Buscando personas que pertenescan al genero: {}", generoEnum.getDescripcion());
        return personaRepository.findByGenero(generoEnum).stream().map(personaMapper::entityToResponse).toList();
    }

    @Override
    public PersonaResponse obtenerPorId(Long id) {
        return personaMapper.entityToResponse(obtenerPersonaOException(id));
    }

    @Override
    public PersonaResponse registrar(PersonaRequest request) {
        log.info("registrando nueva persona {}", request.nombre());

        validarTelefonoUnico(request.telefono());

        Genero genero = Genero.obtenerPorAbreviacion(request.genero());

        String email = generarEmail(request.nombre(), request.apellidoPaterno(), request.apellidoMaterno());

        validarEmailUnico(email);

        Persona persona = personaRepository.save(personaMapper.requestToEntity(request, genero, email));
        log.info("Nueva persona registrada: {}", persona.getNombre());

        return personaMapper.entityToResponse(persona);
    }

    @Override
    public PersonaResponse actualizar(PersonaRequest request, Long id) {
        Persona persona = obtenerPersonaOException(id);
        log.info("actualizando persona con id: {}", id);

        validarTelefonoUnicoActualizado(request.telefono(),id);

        persona.setNombre(request.nombre());
        persona.setApellidoPaterno(request.apellidoPaterno());
        persona.setApellidoMaterno(request.apellidoMaterno());
        persona.setEdad(request.edad());
        persona.setTelefono(request.telefono());

        Genero genero = Genero.obtenerPorAbreviacion(request.genero());
        persona.setGenero(genero);

        String email = generarEmail(request.nombre(), request.apellidoPaterno(), request.apellidoMaterno());

        validarEmailUnicoActualizado(email,id);

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

    private void validarTelefonoUnico(String telefono) {
        log.info("Validando telefono unico...");
        if(personaRepository.existsByTelefono(telefono)) {
            throw new IllegalArgumentException("Ya existe una persona registrada con el teléfono : " + telefono);
        }
    }

    private void validarTelefonoUnicoActualizado(String telefono, Long id) {
        log.info("Validando telefono unico al actulizar...");
        if(personaRepository.existsByTelefonoAndIdNot(telefono, id)) {
            throw new IllegalArgumentException("Ya existe una persona registrada con el teléfono : " + telefono);
        }
    }

    private void validarEmailUnico(String email) {
        log.info("Validando email unico...");
        if (personaRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email generado (" + email + ") ya está en uso.");
        }
    }

    private void validarEmailUnicoActualizado(String email, Long id) {
        log.info("Validando email unico actualizado...");
        if(personaRepository.existsByEmailAndIdNot(email, id)) {
            throw new IllegalArgumentException("El email generado ya esta en uso por otra persona.");
        }
    }
}
