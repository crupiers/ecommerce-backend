package programacion.eCommerceApp.controller.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NewMovimientoStockRequest(

        @NotNull(message = "La cantidad no puede ser nula")
        @Positive
        Integer cantidad,

        @NotNull
        String motivo,

        @NotNull
        String tipoMovimiento
) {


}
