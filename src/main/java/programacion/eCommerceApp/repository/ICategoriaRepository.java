package programacion.ejemplo.repository;

import programacion.ejemplo.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ICategoriaRepository extends JpaRepository  <Categoria,Integer>{

    List<Categoria> findByEstado(int estado);
}

