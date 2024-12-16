package programacion.eCommerceApp.controller.request;

public record NewImagenRequest(

    Integer idProducto,
    String imagenBase64

) {
}
