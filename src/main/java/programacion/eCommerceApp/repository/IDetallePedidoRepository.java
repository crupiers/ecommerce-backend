package programacion.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion.eCommerceApp.model.DetallePedido;

import java.util.List;
import java.util.Optional;

public interface IDetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
    List<DetallePedido> findByEstado(int estado);
    Optional<DetallePedido> findById(int id);
}
