package programacion.eCommerceApp.controller.response;

import java.util.List;

public record ProductoResponse(
        Integer id,
        String nombre,
        String descripcion,
        Double precio,
        Integer stock,
        String nombreCategoria,
        String nombreMarca,
        String nombreTamanio,
        String nombreColor,
        Integer codigoBarra,
        List<MovimientoStockResponse>movimientos
){

}


