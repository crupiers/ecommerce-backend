package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;

public record NewTamañoRequest (

    @NotBlank(message = "La denominación de la marca no puede estar vacio")
    String denominacion

) { }
