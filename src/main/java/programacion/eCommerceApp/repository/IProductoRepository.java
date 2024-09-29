package programacion.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion.eCommerceApp.model.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoRepository extends JpaRepository <Producto, Integer> {
    List<Producto> findByEstado(int estado);
    Optional<Producto> findByNombre(String nombre);
}
