package programacion.ejemplo.service;

import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.model.Categoria;
import java.util.List;

public interface ICategoriaService {

    public List<CategoriaDTO> listar();

    public Categoria buscarPorId(Integer id);

    public CategoriaDTO guardar(CategoriaDTO model);

    public Categoria guardar(Categoria model);

    public void eliminar(Categoria model);
}
