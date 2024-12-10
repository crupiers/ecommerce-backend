package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.controller.response.MovimientoStockResponse;
import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class ProductoMapper {

    public static ProductoResponse toProductoResponse(Producto producto){
        List<MovimientoStockResponse> movimientos = producto.getMovimientos().stream()
                .map(mov -> new MovimientoStockResponse(
                        mov.getId(),
                        mov.getCantidad(),
                        mov.getMotivo(),
                        mov.getTipoMovimiento(),
                        mov.getFechaPedido(),
                        mov.getHoraPedido()))
                .collect(Collectors.toList());
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
                producto.getCodigoBarra(),
                movimientos);
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
                .descripcion((newProductoRequest.descripcion()))
                .build();
    }
}
