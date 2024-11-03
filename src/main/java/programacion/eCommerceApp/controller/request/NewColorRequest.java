package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewColorRequest(
        @NotNull
        @Size(min = 2, max = 24, message = "EL NOMBRE DEBE IR DE ENTRE 2 Y 24 CARACTERES")
        @Pattern(regexp = "^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*$", message = "SÓLO PUEDEN HABER LETRAS Y NÚMEROS, NO TIENE QUE HABER ESPACIOS DOBLES NI ESPACIO AL INICIO NI AL FINAL")
        String nombre,

        @NotNull
        @Pattern(regexp = "^[^\\s]+(\\s[^\\s]+)*$", message = "LA DESCRIPCIÓN NO TIENE QUE HABER ESPACIOS DOBLES NI ESPACIO AL INICIO NI AL FINAL, NI PUEDE ESTAR VACÍO")
        String descripcion
) {
        //si no paso nada como descripcion en el json entonces le seteo el nombre
        public NewColorRequest{
               if(descripcion==null){
                       descripcion=nombre;
               }
        }
}
