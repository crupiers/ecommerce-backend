package programacion.eCommerceApp.service;

import programacion.eCommerceApp.controller.request.NewTamañoRequest;
import programacion.eCommerceApp.controller.response.TamañoResponse;
import programacion.eCommerceApp.model.Tamaño;
import java.util.List;

public interface ITamañoService {

    TamañoResponse crear(NewTamañoRequest newTamañoRequest);

    List<TamañoResponse> listar();

    Tamaño buscarPorId (Integer id);

    void recuperar (Tamaño tamaño);

    void eliminar (Tamaño tamaño);

}
