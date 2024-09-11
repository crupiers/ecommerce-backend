package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;

public record NewCategoriaRequest (

        @NotBlank(message = "El nombre de la categoria no puede estar vacio.")
        String nombre

) { }
