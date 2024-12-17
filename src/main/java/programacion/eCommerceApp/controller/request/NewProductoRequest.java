package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewProductoRequest(
        @NotNull
        @Size(min = 2, max = 64, message = "EL NOMBRE DEBE IR DE ENTRE 2 Y 64 CARACTERES")
        @Pattern(regexp = "^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*$", message = "SÓLO PUEDEN HABER LETRAS Y NÚMEROS, NO TIENE QUE HABER ESPACIOS DOBLES NI ESPACIO AL INICIO NI AL FINAL")
        String nombre,

        @NotNull
        @Pattern(regexp = "^[^\\s]+(\\s[^\\s]+)*$", message = "LA DESCRIPCIÓN NO TIENE QUE HABER ESPACIOS DOBLES NI ESPACIO AL INICIO NI AL FINAL, NI PUEDE ESTAR VACÍO")
        String descripcion,

        @NotNull
        //@Pattern(regexp = "^[0-9]+$", message = "SE DEBE INGRESAR UN NÚMERO ENTERO, NO TIENE QUE HABER ESPACIOS")
        Integer stock,

        @NotNull
        //@Pattern(regexp = "^[0-9]+$", message = "SE DEBE INGRESAR UN NÚMERO ENTERO, NO TIENE QUE HABER ESPACIOS")
        Integer codigoBarra,

        @NotNull
        //@Pattern(regexp = "^(([0-9]+)|([0-9]+[.][0-9]+))$", message = "SE DEBE INGRESAR UN NÚMERO ENTERO O DECIMAL CON PUNTO Y NO COMA, NO TIENE QUE HABER ESPACIOS")
        Double precio,

        @NotNull
        Integer umbral,

        @NotNull
        //@Pattern(regexp = "^[0-9]+$", message = "SE DEBE INGRESAR UN NÚMERO ENTERO, NO TIENE QUE HABER ESPACIOS")
        Integer colorId,

        @NotNull
        //@Pattern(regexp = "^[0-9]+$", message = "SE DEBE INGRESAR UN NÚMERO ENTERO, NO TIENE QUE HABER ESPACIOS")
        Integer tamanioId,

        @NotNull
        //@Pattern(regexp = "^[0-9]+$", message = "SE DEBE INGRESAR UN NÚMERO ENTERO, NO TIENE QUE HABER ESPACIOS")
        Integer categoriaId,

        @NotNull
        //@Pattern(regexp = "^[0-9]+$", message = "SE DEBE INGRESAR UN NÚMERO ENTERO, NO TIENE QUE HABER ESPACIOS")
        Integer marcaId

) {}
