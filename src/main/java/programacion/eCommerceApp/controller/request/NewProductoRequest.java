package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewProductoRequest(
        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @NotNull(message = "El stock no puede ser nulo")
        Integer stock,

        @NotNull(message = "El código de barra no puede ser nulo")
        Integer codigoBarra,

        @NotNull(message = "El precio no puede ser nulo")
        Double precio,

        @NotNull(message = "El nombre del color no puede ser nulo")
        String nombreColor,

        @NotNull(message = "El ID del tamaño no puede ser nulo")
        Integer tamañoId,

        @NotNull(message = "El ID de la categoría no puede ser nulo")
        Integer categoriaId,

        @NotNull(message = "El ID de la marca no puede ser nulo")
        Integer marcaId
) { }
