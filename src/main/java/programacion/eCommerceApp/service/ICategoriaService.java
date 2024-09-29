package programacion.eCommerceApp.service;

import java.util.List;

import programacion.eCommerceApp.controller.request.NewCategoriaRequest;
import programacion.eCommerceApp.controller.response.CategoriaResponse;
import programacion.eCommerceApp.model.Categoria;

public interface ICategoriaService {

    List<CategoriaResponse> listar();

    Categoria buscarPorId(Integer id);

    CategoriaResponse crear(NewCategoriaRequest newCategoriaRequest);

    CategoriaResponse actualizar(NewCategoriaRequest newCategoriaRequest, Integer id);

    void eliminar(Categoria model);

    void recuperar(Categoria model);
}
