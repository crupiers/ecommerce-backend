package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record NewColorRequest(
        @NotNull
        @Pattern(regexp = "^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*$", message = "SÓLO PUEDEN HABER LETRAS Y NÚMEROS, NO TIENE QUE HABER ESPACIOS DOBLES NI ESPACIO AL INICIO NI AL FINAL")
        String nombre,

        //PARA EL COLOR VOY A PERMITIR QUE NO SE PASE NINGUNA DESCRIPCIÓN POR COMODIDAD
        @Pattern(regexp = "^[^\\s][^\\s]+(\\s[^\\s]+)*$", message = "NO TIENE QUE HABER ESPACIOS DOBLES NI ESPACIO AL INICIO NI AL FINAL")
        String descripcion
) {}
