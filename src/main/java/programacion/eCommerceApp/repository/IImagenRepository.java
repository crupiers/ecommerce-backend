package programacion.eCommerceApp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import programacion.eCommerceApp.model.Imagen;

public interface IImagenRepository extends JpaRepository<Imagen, Integer> {
}
