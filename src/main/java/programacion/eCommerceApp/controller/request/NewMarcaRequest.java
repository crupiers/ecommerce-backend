package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record NewMarcaRequest(

    @NotBlank(message = "La denominación de la marca no puede estar vacio")
    @Pattern(regexp = "^[A-Za-z\s]+$", message = "La denominación solo debe contener letras y espacios")
    String denominacion,

    @NotBlank(message = "La observación de la marca no puede estar vacio")
    String observaciones

) { }