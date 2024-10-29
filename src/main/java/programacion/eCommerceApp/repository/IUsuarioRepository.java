package programacion.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import programacion.eCommerceApp.model.Usuario;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByNombre(String nombre);

}
