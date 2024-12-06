package programacion.eCommerceApp.controller.response;

public record DetallePedidoResponse(

    Integer id,
    Integer cantidad,
    Double subtotal,
    Integer idProducto,
    Integer estado

)
{ }
