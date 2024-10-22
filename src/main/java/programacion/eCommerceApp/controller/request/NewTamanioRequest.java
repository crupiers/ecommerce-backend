package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record NewTamanioRequest(

    @NotBlank(message = "EL NOMBRE DEL TAMAÑO PUEDE ESTAR VACÍO")
    @Pattern(regexp = "^[A-Za-z\s]+$", message = "EL NOMBRE SÓLO PUEDE TENER LETRAS Y ESPACIOS")
    String nombre,

    @NotBlank(message = "LA DESCRIPCIÓN DEL TAMAÑO NO PUEDE ESTAR VACÍA")
    String descripcion

) { }
