package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record NewCategoriaRequest (

        @NotBlank(message = "El nombre de la categoria no puede estar vacio.")
        @Pattern(regexp = "^[A-Za-z\s]+$", message = "El nombre solo debe contener letras y espacios")
        String nombre

) { }
