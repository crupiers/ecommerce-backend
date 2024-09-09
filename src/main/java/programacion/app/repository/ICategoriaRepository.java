package programacion.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import programacion.app.model.Categoria;

import java.util.List;

public interface ICategoriaRepository extends JpaRepository  <Categoria,Integer>{

    List<Categoria> findByEstado(int estado);
}

