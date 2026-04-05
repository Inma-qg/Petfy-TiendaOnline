package petfy.modelo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import petfy.modelo.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{


    // Buscar pedidos de un usuario
    List<Pedido> findByUsuarioId(Integer usuarioId);

    // Buscar pedidos por estado
    List<Pedido> findByEstado(Pedido.EstadoPedido estado);

    // Buscar pedidos en un rango de fechas
    List<Pedido> findByFechaPedidoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

}
