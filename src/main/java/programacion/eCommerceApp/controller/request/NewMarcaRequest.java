package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record NewMarcaRequest(

    @NotBlank(message = "EL NOMBRE DE LA MARCA NO PUEDE ESTAR VACÍO")
    //FALTA ACLARAR ESTA REGEXP!!! NO USEN GPT AMIGOS
    @Pattern(regexp = "^[A-Za-z\s]+$", message = "EL NOMBRE SÓLO PUEDE TENER LETRAS Y ESPACIOS")
    String nombre,

    @NotBlank(message = "LA DESCRIPCIÓN DE LA MARCA NO PUEDE SER VACÍA")
    String descripcion
){}

