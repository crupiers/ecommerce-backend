package programacion.eCommerceApp.service;

import programacion.eCommerceApp.DTO.CategoriaDTO;
import programacion.eCommerceApp.model.Categoria;
import java.util.List;

public interface ICategoriaService {

    public List<CategoriaDTO> listar();

    public Categoria buscarPorId(Integer id);

    public CategoriaDTO guardar(CategoriaDTO model);

    public Categoria guardar(Categoria model);

    public void eliminar(Categoria model);
}
