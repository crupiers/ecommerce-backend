package programacion.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion.eCommerceApp.model.Color;

import java.util.List;

public interface IColorRepository extends JpaRepository<Color, String> {
    List<Color> findByEstado(int estado); //busco los colores según su disponibilidad
    //este metodo permite tener los registros que están en "true" o en "false"
    //es decir que nos permite ver cuales están logicamente eliminados
}
