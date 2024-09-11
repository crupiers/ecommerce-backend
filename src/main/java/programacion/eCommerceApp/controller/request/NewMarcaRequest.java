package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;

public record NewMarcaRequest(

    @NotBlank(message = "La denominación de la marca no puede estar vacio")
    String denominacion,

    @NotBlank(message = "La observación de la marca no puede estar vacio")
    String observaciones

) { }