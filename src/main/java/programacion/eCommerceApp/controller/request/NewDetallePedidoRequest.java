package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
public record NewDetallePedidoRequest(

    //@Pattern(regexp = "^[0-9]+$", message = "SE DEBE INGRESAR UN NÚMERO ENTERO, NO TIENE QUE HABER ESPACIOS")
    @NotNull(message = "EL PRODUCTO NO PUEDE SER NULO")
    Integer idProducto,
    //@Pattern(regexp = "^[0-9]+$", message = "SE DEBE INGRESAR UN NÚMERO ENTERO, NO TIENE QUE HABER ESPACIOS")
    @NotNull(message = "LA CANTIDAD NO PUEDE SER NULA")
    @Positive(message = "LA CANTIDAD DEBE SER MAYOR A 0")
    Integer cantidad

)
{ }
