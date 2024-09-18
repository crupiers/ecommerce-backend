package programacion.eCommerceApp.controller.response;

public record ProductoResponse(
        Integer id,
        String nombre,
        String nombreColor,
        Integer stock,
        Integer codigoBarra,
        Double precio,
        String denominacionTamaño,
        String denominacionMarca,
        String nombreCategoria) {


}
