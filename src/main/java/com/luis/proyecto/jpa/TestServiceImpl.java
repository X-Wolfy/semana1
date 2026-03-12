package com.luis.proyecto.jpa;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class TestServiceImpl implements TestService {
    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;

    @Override
    public void registrarUsuario() {
        Usuario usuario = new Usuario(null, "Test",null);
        usuarioRepository.save(usuario);
    }

    @Override
    public void agregarPerdido() {
        Usuario usuario = usuarioRepository.findById(1L).orElse(null);
        Pedido pedido = new Pedido(null, BigDecimal.valueOf(500.00), usuario);
        pedidoRepository.save(pedido);
    }

    @Override
    public Map<Object, String> consultarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().collect(Collectors.toMap(
                Usuario::getId,
                usuario -> {
                    String pedidos = usuario.getPedidos()
                            .stream()
                            .map(p -> "Pedido " + p.getId() + " Total: " + p.getTotal())
                            .collect(Collectors.joining(", "));

                    return "Usuario: " + usuario.getNombre() + " | Pedidos: [" + pedidos + "]";
                }
        ));
    }

    @Override
    public Map<Object, String> consultarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        return pedidos.stream().collect(Collectors.toMap(
                Pedido::getId,
                pedido -> "Total: " + pedido.getTotal() +
                        " | Usuario: " + pedido.getUsuario().getNombre()
        ));
    }
}
