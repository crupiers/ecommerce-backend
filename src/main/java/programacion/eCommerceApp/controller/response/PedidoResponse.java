package programacion.eCommerceApp.controller.response;

import java.util.List;

public record PedidoResponse(
    Integer id,
    String horaPedido,
    String fechaPedido,
    String nombreUsuario,
    List<Integer> idDetallesPedido,
    Double total,
    Integer estado
)
{ }
