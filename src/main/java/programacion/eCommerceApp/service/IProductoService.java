package programacion.eCommerceApp.service;

import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.model.Producto;

import java.util.List;

public interface IProductoService {
    ProductoResponse crear(NewProductoRequest newProductoRequest);

    List<ProductoResponse> listar();

    Producto buscarPorId (Integer id);

    void recuperar (Producto producto);

    void eliminar (Producto producto);

    ProductoResponse actualizar (NewProductoRequest newProductoRequest , Integer id);

    Producto buscarPorNombre (String nombre);
}
