package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record NewColorRequest( //debo pasar los datos necesarios para crear un color
        @NonNull //verifico que no me pase un objeto nulo
        @NotBlank(message = "EL COLOR NO PUEDE SER ESPACIOS VACÍOS") //verifico que el string no sean solo espacios
        String nombre
) {
}
