package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record NewTamanioRequest(

    @NotBlank(message = "La denominación de la marca no puede estar vacio")
    @Pattern(regexp = "^[A-Za-z\s]+$", message = "La denominación solo debe contener letras y espacios")
    String denominacion,

    @NotBlank(message = "Las descripcion de la marca no pueden estar vacias")
    String observaciones

) { }
