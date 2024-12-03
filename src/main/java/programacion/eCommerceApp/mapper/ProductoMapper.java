package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.model.Color;
import programacion.eCommerceApp.model.Tamanio;
import programacion.eCommerceApp.model.Categoria;
import programacion.eCommerceApp.model.Marca;

public class ProductoMapper {

    public ProductoMapper() {
    }

    public static ProductoResponse toProductoResponse(final Producto producto) {
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria().getNombre(),
                producto.getMarca().getNombre(),
                producto.getTamanio().getNombre(),
                producto.getColor().getNombre(),
                producto.getCodigoBarra());
    }
    public static Producto toEntity(final NewProductoRequest newProductoRequest,
                                    final Color color,
                                    final Tamanio tamanio,
                                    final Categoria categoria,
                                    final Marca marca) {
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
                .descripcion((newProductoRequest.descripcion()))
                .build();
    }
}
