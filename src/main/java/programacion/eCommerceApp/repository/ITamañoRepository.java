package programacion.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import programacion.eCommerceApp.model.Tamaño;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITamañoRepository extends JpaRepository<Tamaño, Integer> {
    List<Tamaño> findByEstado(int estado);
    Optional<Tamaño> findByDenominacion(String denominacion);
}
