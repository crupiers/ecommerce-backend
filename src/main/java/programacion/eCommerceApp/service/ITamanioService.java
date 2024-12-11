package programacion.eCommerceApp.service;

import org.springframework.http.ResponseEntity;
import programacion.eCommerceApp.controller.request.NewTamanioRequest;
import programacion.eCommerceApp.controller.response.TamanioResponse;
import java.util.List;

public interface ITamanioService {
    TamanioResponse crear(NewTamanioRequest newTamanioRequest);
    List<TamanioResponse> listar();
    ResponseEntity<TamanioResponse> buscarPorId (Integer id);
    ResponseEntity<Void> eliminar (Integer id);
    ResponseEntity<Void> recuperar (Integer id);
    TamanioResponse actualizar(NewTamanioRequest newTamanioRequest, Integer id);
    List<TamanioResponse> listarParaAuditoria();
}
