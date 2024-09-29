package programacion.eCommerceApp.service;

import java.util.List;
import programacion.eCommerceApp.controller.request.NewMarcaRequest;
import programacion.eCommerceApp.controller.response.MarcaResponse;
import programacion.eCommerceApp.model.Marca;

public interface IMarcaService {

    List<MarcaResponse> listar();

    Marca buscarPorId(Integer id);

    MarcaResponse crear(NewMarcaRequest newMarcaRequest);

    void eliminar(Marca model);

    void recuperar(Marca model);

    MarcaResponse actualizar(NewMarcaRequest newMarcaRequest, Integer id);
}
