package programacion.eCommerceApp.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public record NewMarcaRequest(
        @JsonProperty("nombre")
        @NotNull
        @Size(min = 2, max = 32, message = "EL NOMBRE DEBE IR DE ENTRE 2 Y 32 CARACTERES")
        @Pattern(regexp = "^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*$", message = "SÓLO PUEDEN HABER LETRAS Y NÚMEROS, NO TIENE QUE HABER ESPACIOS DOBLES NI ESPACIO AL INICIO NI AL FINAL")
        String nombre,

        @JsonProperty("descripcion")
        @NotNull
        @Pattern(regexp = "^[^\\s]+(\\s[^\\s]+)*$", message = "LA DESCRIPCIÓN NO TIENE QUE HABER ESPACIOS DOBLES NI ESPACIO AL INICIO NI AL FINAL, NI PUEDE ESTAR VACÍO")
        String descripcion
){
        @JsonCreator
        public NewMarcaRequest {
        }


}
