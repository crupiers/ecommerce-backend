package programacion.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion.eCommerceApp.model.Pedido;
import programacion.eCommerceApp.model.Usuario;

import java.util.List;

public interface IPedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByEstado(int estado);
    Pedido findById(int id);
    List<Pedido> findAllByUsuario(Usuario usuario);
}
