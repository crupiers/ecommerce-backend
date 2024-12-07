package programacion.eCommerceApp.controller.response;

public record ProductoResponse(
        Integer id,
        String nombre,
        String descripcion,
        Double precio,
        Integer umbral,
        Integer stock,
        String nombreCategoria,
        String nombreMarca,
        String nombreTamanio,
        String nombreColor,
        Integer codigoBarra
) {
}
