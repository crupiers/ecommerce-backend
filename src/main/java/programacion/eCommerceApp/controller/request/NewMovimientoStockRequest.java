package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record NewMovimientoStockRequest(

        @NotNull
        Integer cantidad,

        @Size(min = 2, max = 32, message = "EL MOTIVO DEBE IR DE ENTRE 2 Y 32 CARACTERES")
        @NotNull
        String motivo,

        @NotNull
        String tipoMovimiento
) {


}
