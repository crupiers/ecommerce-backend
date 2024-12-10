package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotNull;

public record NewMovimientoStockRequest(

        @NotNull
        Integer cantidad,

        @NotNull
        String motivo,

        @NotNull
        String tipoMovimiento
) {


}
