package programacion.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion.eCommerceApp.model.Color;

import java.util.List;
import java.util.Optional;

public interface IColorRepository extends JpaRepository<Color, Integer> {
    List<Color> findByEstado(int estado); //busco los colores según su disponibilidad
    //este metodo permite tener los registros que están en "true" o en "false"
    //es decir que nos permite ver cuales están logicamente eliminados
    Optional<Color> findByNombre (String nombre);
}
