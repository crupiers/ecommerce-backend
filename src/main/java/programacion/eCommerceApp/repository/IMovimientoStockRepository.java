package programacion.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion.eCommerceApp.model.MovimientoStock;
import programacion.eCommerceApp.model.Producto;

import java.util.List;

public interface IMovimientoStockRepository extends JpaRepository<MovimientoStock, Integer> {
    List<MovimientoStock> findByProducto(Producto producto); //buscar por producto
    List<MovimientoStock> findByTipoMovimiento(String tipoMovimiento); //buscar por tipo movimiento
}
