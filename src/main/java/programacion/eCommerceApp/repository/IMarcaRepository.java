package programacion.eCommerceApp.repository;

import programacion.ejemplo.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IMarcaRepository extends JpaRepository  <Marca,Integer>{

    List<Marca> findByEstado(int estado);
}

