package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewProductoRequest(
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 1, max = 24, message = "El nombre debe tener entre 1 y 24 caracteres")
        String nombre,

        @NotNull(message = "El stock no puede ser nulo")
        Integer stock,

        @NotNull(message = "El código de barra no puede ser nulo")
        Integer codigoBarra,

        @NotNull(message = "El precio no puede ser nulo")
        Double precio,

        @NotNull(message = "El ID del color no puede ser nulo")
        Integer colorId,

        @NotNull(message = "El ID del tamaño no puede ser nulo")
        Integer tamanioId,

        @NotNull(message = "El ID de la categoría no puede ser nulo")
        Integer categoriaId,

        @NotNull(message = "El ID de la marca no puede ser nulo")
        Integer marcaId
) { }
