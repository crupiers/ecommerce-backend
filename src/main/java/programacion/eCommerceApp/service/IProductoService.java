package programacion.eCommerceApp.service;

import org.springframework.http.ResponseEntity;
import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.controller.response.ProductoResponse;
import java.util.List;

public interface IProductoService {
    ProductoResponse crear(NewProductoRequest newProductoRequest);
    ProductoResponse actualizar (NewProductoRequest newProductoRequest , Integer id);
    List<ProductoResponse> listar();
    ResponseEntity<ProductoResponse> buscarPorId (Integer id);
    ResponseEntity<Void> eliminar (Integer id);
    ResponseEntity<Void> recuperar (Integer id);
    ProductoResponse actualizarStock (Integer id, String motivo, Integer cantidad);
}
