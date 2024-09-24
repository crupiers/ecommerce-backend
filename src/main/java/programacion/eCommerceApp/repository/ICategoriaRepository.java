package programacion.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion.eCommerceApp.model.Categoria;
import java.util.List;
import java.util.Optional;

public interface ICategoriaRepository extends JpaRepository <Categoria,Integer>{

    List<Categoria> findByEstado(int estado);
    Optional<Categoria> findByNombre(String nombre);
    Optional<Categoria> findByIdAndEstado(Integer id, int estado);
}

