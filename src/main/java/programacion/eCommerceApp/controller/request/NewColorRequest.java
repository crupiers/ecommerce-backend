package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record NewColorRequest( //debo pasar los datos necesarios para crear un color
        @NotNull //verifico que no me pase un objeto nulo
        @NotBlank(message = "EL COLOR NO PUEDE SER ESPACIOS VACÍOS") //verifico que el string no sean solo espacios
        //validacion de que no se ingrese un caracter que no sea letra en el nombre
        @Pattern(regexp = "^[A-Za-z\s]+$", message = "La denominación solo debe contener letras y espacios")
        String nombre

) {
}
