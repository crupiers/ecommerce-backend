package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record NewTamañoRequest (

    @NotBlank(message = "La denominación de la marca no puede estar vacio")
    @Pattern(regexp = "^[A-Za-z\s]+$", message = "La denominación solo debe contener letras y espacios")
    String denominacion,

    @NotBlank(message = "Las observaciones de la marca no pueden estar vacias")
    String observaciones

) { }
