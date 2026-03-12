package com.luis.proyecto.jpa;

import java.util.Map;

public interface TestService {
    void registrarUsuario();
    void agregarPerdido();
    Map<Object,String> consultarUsuarios();
    Map<Object,String> consultarPedidos();
}
