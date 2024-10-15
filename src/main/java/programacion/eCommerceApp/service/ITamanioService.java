package programacion.eCommerceApp.service;

import programacion.eCommerceApp.controller.request.NewTamanioRequest;
import programacion.eCommerceApp.controller.response.TamanioResponse;
import programacion.eCommerceApp.model.Tamanio;
import java.util.List;

public interface ITamanioService {

    TamanioResponse crear(NewTamanioRequest newTamanioRequest);
    List<TamanioResponse> listar();
    Tamanio buscarPorId (Integer id);
    void recuperar (Tamanio tamanio);
    void eliminar (Tamanio tamanio);
    TamanioResponse actualizar(NewTamanioRequest newTamanioRequest, Integer id);

}
