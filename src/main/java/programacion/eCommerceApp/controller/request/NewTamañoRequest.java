package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;

public record NewTamañoRequest (

    @NotBlank(message = "La denominación de la marca no puede estar vacio")
    String denominacion,

    @NotBlank(message = "Las observaciones de la marca no pueden estar vacias")
    String observaciones

) { }
