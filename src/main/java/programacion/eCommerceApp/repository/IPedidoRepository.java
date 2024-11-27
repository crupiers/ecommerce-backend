package programacion.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion.eCommerceApp.model.Pedido;

import java.util.List;

public interface IPedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByEstado(int estado);
}
