package programacion.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import programacion.eCommerceApp.model.Tamanio;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITamanioRepository extends JpaRepository<Tamanio, Integer> {
    List<Tamanio> findByEstado(boolean estado);
    Optional<Tamanio> findByDenominacion(String denominacion);
    Optional<Tamanio> findByObservaciones(String observaciones);
}
