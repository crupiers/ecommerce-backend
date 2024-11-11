package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewTamanioRequest(
        @NotNull
        @Size(min = 2, max = 32, message = "EL NOMBRE DEBE IR DE ENTRE 2 Y 32 CARACTERES")
        @Pattern(regexp = "^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*$", message = "SÓLO PUEDEN HABER LETRAS Y NÚMEROS, NO TIENE QUE HABER ESPACIOS DOBLES NI ESPACIO AL INICIO NI AL FINAL")
        String nombre,

        @NotNull
        @Pattern(regexp = "^[^\\s]+(\\s[^\\s]+)*$", message = "LA DESCRIPCIÓN NO TIENE QUE HABER ESPACIOS DOBLES NI ESPACIO AL INICIO NI AL FINAL, NI PUEDE ESTAR VACÍO")
        String descripcion
) {}
