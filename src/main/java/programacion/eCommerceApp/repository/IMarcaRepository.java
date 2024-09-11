package programacion.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion.eCommerceApp.model.Marca;
import java.util.List;
import java.util.Optional;

public interface IMarcaRepository extends JpaRepository <Marca,Integer>{
    List<Marca> findByEstado(int estado);
    Optional<Marca> findByDenominacion(String denominacion);
}