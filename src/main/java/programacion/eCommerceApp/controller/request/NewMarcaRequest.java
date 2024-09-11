package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotNull;

public record NewMarcaRequest(

    @NotNull(message = "La denominación de la marca no puede ser nula")
    String denominacion,

    @NotNull(message = "La observación de la marca no puede ser nula")
    String observaciones

) { }