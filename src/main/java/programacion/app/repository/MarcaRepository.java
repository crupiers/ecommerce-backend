package programacion.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import programacion.app.model.Marca;

import java.util.List;

public interface MarcaRepository extends JpaRepository  <Marca,Integer>{

    List<Marca> findByEstado(int estado);
}

