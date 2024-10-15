package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record NewMarcaRequest(

    @NotBlank(message = "El nombre de la marca no puede estar vacio")
    //FALTA ACLARAR ESTA REGEXP!!! NO USEN GPT AMIGOS
    @Pattern(regexp = "^[A-Za-z\s]+$", message = "El nombre sólo debe contener letras y espacios")
    String nombre,

    @NotBlank(message = "La descripción de la marca no puede estar vacio")
    String descripcion
){}

