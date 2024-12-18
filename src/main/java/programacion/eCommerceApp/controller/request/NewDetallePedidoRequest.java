package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotNull;
public record NewDetallePedidoRequest(

    //@Pattern(regexp = "^[0-9]+$", message = "SE DEBE INGRESAR UN NÚMERO ENTERO, NO TIENE QUE HABER ESPACIOS")
    @NotNull(message = "El producto no puede ser nulo")
    Integer idProducto,
    //@Pattern(regexp = "^[0-9]+$", message = "SE DEBE INGRESAR UN NÚMERO ENTERO, NO TIENE QUE HABER ESPACIOS")
    @NotNull(message = "La cantidad no puede ser nula")
    Integer cantidad

)
{ }
