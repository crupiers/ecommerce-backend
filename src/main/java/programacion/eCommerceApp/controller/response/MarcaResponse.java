package programacion.eCommerceApp.controller.response;

public record MarcaResponse(
        Integer id,
        String nombre,
        String descripcion,
        int estado
) {}
