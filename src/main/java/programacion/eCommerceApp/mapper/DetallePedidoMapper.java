package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewDetallePedidoRequest;
import programacion.eCommerceApp.controller.response.DetallePedidoResponse;
import programacion.eCommerceApp.model.DetallePedido;
import programacion.eCommerceApp.model.Producto;

public class DetallePedidoMapper {

    public static DetallePedido toEntity(NewDetallePedidoRequest newDetallePedidoRequest, Producto producto) {
        return DetallePedido.builder()
            .cantidad(newDetallePedidoRequest.cantidad())
            .producto(producto)
            .build();
    }

    public static DetallePedidoResponse toDetallePedidoResponse(DetallePedido detallePedido) {
        return new DetallePedidoResponse(
            detallePedido.getId(),
            detallePedido.getCantidad(),
            detallePedido.getSubtotal(),
            detallePedido.getProducto().getId(),
            detallePedido.getEstado()
        );
    }
}
