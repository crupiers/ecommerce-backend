package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.controller.response.MovimientoStockResponse;
import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.model.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoMapper {

    public static ProductoResponse toProductoResponse(Producto producto) {
        List<MovimientoStockResponse> movimientos = producto.getMovimientos() != null
            ? producto.getMovimientos().stream()
            .map(mov -> new MovimientoStockResponse(
                mov.getId(),
                mov.getCantidad(),
                mov.getMotivo(),
                mov.getTipoMovimiento(),
                mov.getCreatedBy(),
                mov.getFechaPedido(),
                mov.getHoraPedido()))
            .collect(Collectors.toList())
            : Collections.emptyList();
        return new ProductoResponse(
            producto.getId(),
            producto.getNombre(),
            producto.getDescripcion(),
            producto.getPrecio(),
            producto.getUmbral(),
            producto.getStock(),
            producto.getCategoria().getNombre(),
            producto.getMarca().getNombre(),
            producto.getTamanio().getNombre(),
            producto.getColor().getNombre(),
            producto.getCodigoBarra(),
            movimientos,
            producto.getCreatedBy(),
            producto.getCreatedAt(),
            producto.getUpdatedBy(),
            producto.getUpdatedAt(),
            producto.getDeletedAt(),
            producto.getEstado()
        );
    }

    public static Producto toEntity(NewProductoRequest newProductoRequest, Color color, Tamanio tamanio, Categoria categoria, Marca marca) {
        return Producto.builder()
            .nombre(newProductoRequest.nombre())
            .color(color) // Entidad Color
            .tamanio(tamanio) // Entidad Tamaño
            .categoria(categoria) // Entidad Categoria
            .marca(marca) // Entidad Marca
            .precio(newProductoRequest.precio())
            .umbral(newProductoRequest.umbral())
            .stock(newProductoRequest.stock())
            .codigoBarra(newProductoRequest.codigoBarra())
            .estado(0) // Estado por defecto, ajusta si es necesario
            .descripcion((newProductoRequest.descripcion()))
            .build();
    }
}
