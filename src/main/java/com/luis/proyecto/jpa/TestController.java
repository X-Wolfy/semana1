package com.luis.proyecto.jpa;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/jpa")
@AllArgsConstructor
public class TestController {
    private final TestService testService;
    @PostMapping("/usuario")
    public ResponseEntity<Void> registrarUsuario(){
        testService.registrarUsuario();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/pedido")
    public ResponseEntity<Void> agregarPedido(){
        testService.agregarPerdido();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuarios")
    public ResponseEntity<Map<Object, String>> consultarUsuarios(){
        return ResponseEntity.ok(testService.consultarUsuarios());
    }

    @GetMapping("/pedidos")
    public ResponseEntity<Map<Object, String>> consultarPedidos(){
        return ResponseEntity.ok(testService.consultarPedidos());
    }
}
