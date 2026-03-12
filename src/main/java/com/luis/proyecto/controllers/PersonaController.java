package com.luis.proyecto.controllers;

import com.luis.proyecto.dto.PersonaRequest;
import com.luis.proyecto.dto.PersonaResponse;
import com.luis.proyecto.services.PersonaService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/personas")
@AllArgsConstructor
@Validated
public class PersonaController {
    private final PersonaService personaService;

    @GetMapping
    public ResponseEntity<List<PersonaResponse>> listar() {
        return ResponseEntity.ok(personaService.listar());
    }

    @GetMapping("/nombre")
    public ResponseEntity<List<PersonaResponse>> obtenerPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(personaService.obtenerPorNombre(nombre));
    }

    @GetMapping("/email")
    public ResponseEntity<List<PersonaResponse>> obtenerPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(personaService.obtenerPorEmail(email));
    }

    @GetMapping("/edad")
    public ResponseEntity<List<PersonaResponse>> obtenerPorEdad(
            @RequestParam @Positive(message = "La edad minima debe ser positiva") Short min,
            @RequestParam @Positive(message = "La edad maxima debe ser positiva") Short max) {
        return ResponseEntity.ok(personaService.obtenerPorRangoEdad(min, max));
    }

    @GetMapping("/telefono")
    public ResponseEntity<List<PersonaResponse>> obtenerPorTelefono(@RequestParam String telefono) {
        return ResponseEntity.ok(personaService.obtenerPorTelefono(telefono));
    }

    @GetMapping("/genero")
    public ResponseEntity<List<PersonaResponse>> obtenerPorGenero(@RequestParam Character genero) {
        return ResponseEntity.ok(personaService.obtenerPorGenero(genero));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaResponse> obtenerPorId(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        return ResponseEntity.ok(personaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PersonaResponse> registrar(@Validated @RequestBody PersonaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personaService.registrar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaResponse> actualizar(
            @Validated @RequestBody PersonaRequest request,
            @PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        return ResponseEntity.ok(personaService.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        personaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
