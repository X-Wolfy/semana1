package com.luis.proyecto.mappers;

import com.luis.proyecto.dto.PersonaRequest;
import com.luis.proyecto.dto.PersonaResponse;
import com.luis.proyecto.entities.Persona;
import com.luis.proyecto.enums.Genero;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {
    public Persona requestToEntity(PersonaRequest request) {
        if(request == null) return null;
        return Persona.builder()
                .nombre(request.nombre())
                .apellidoPaterno(request.apellidoPaterno())
                .apellidoMaterno(request.apellidoMaterno())
                .edad(request.edad())
                .telefono(request.telefono())
                .build();
    }

    public Persona requestToEntity(PersonaRequest request, Genero genero, String email) {
        if(request == null) return null;

        Persona persona = requestToEntity(request);
        persona.setGenero(genero);
        persona.setEmail(email);
        return persona;
    }

    public PersonaResponse entityToResponse(Persona entity) {
        if(entity == null) return null;
        return new PersonaResponse(
                entity.getId(),
                String.join(" ",
                        entity.getNombre(),
                        entity.getApellidoPaterno(),
                        entity.getApellidoMaterno()),
                entity.getEdad(),
                entity.getGenero().getDescripcion(),
                entity.getEmail(),
                entity.getTelefono()
        );
    }
}
