package programacion.app.service;

import java.util.List;

import programacion.app.DTO.CategoriaDTO;
import programacion.app.model.Categoria;

public interface ICategoriaService {

    public List<CategoriaDTO> listar();

    public Categoria buscarPorId(Integer id);

    public CategoriaDTO guardar(CategoriaDTO model);

    public Categoria guardar(Categoria model);

    public void eliminar(Categoria model);

    public void recuperar(Categoria model);
}
