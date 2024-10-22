package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.model.*;

public class ProductoMapper {

    public static ProductoResponse toProductoResponse(Producto producto){
        return new ProductoResponse(producto.getId(),
                producto.getNombre(),
                producto.getColor().getNombre(),
                producto.getStock(),
                producto.getCodigoBarra(),
                producto.getPrecio(),
                producto.getTamanio().getNombre(),
                producto.getMarca().getNombre(),
                producto.getCategoria().getNombre());
    }
    public static Producto toEntity(NewProductoRequest newProductoRequest, Color color, Tamanio tamanio, Categoria categoria, Marca marca) {
        return Producto.builder()
                .nombre(newProductoRequest.nombre())
                .color(color) // Entidad Color
                .tamanio(tamanio) // Entidad Tamaño
                .categoria(categoria) // Entidad Categoria
                .marca(marca) // Entidad Marca
                .precio(newProductoRequest.precio())
                .stock(newProductoRequest.stock())
                .codigoBarra(newProductoRequest.codigoBarra())
                .estado(0) // Estado por defecto, ajusta si es necesario
                .build();
    }
}
