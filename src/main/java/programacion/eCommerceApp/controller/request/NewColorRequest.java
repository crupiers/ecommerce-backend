package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewColorRequest( //debo pasar los datos necesarios para crear un color
        @NotNull //verifico que no me pase un objeto nulo
        @NotBlank(message = "EL COLOR NO PUEDE SER ESPACIOS VACÍOS") //verifico que el string no sean solo espacios
        String nombre
) {
}
