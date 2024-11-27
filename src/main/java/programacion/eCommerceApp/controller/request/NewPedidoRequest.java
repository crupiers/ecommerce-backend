package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record NewPedidoRequest(

    @NotNull(message = "El usuario no puede ser nulo")
    Integer idUsuario,

    @NotNull(message = "Los detalles de pedido no pueden ser nulos")
    List<Integer> idDetallesPedido

)
{ }
