package programacion.eCommerceApp.controller.response;

public record ColorResponse(Integer id, String nombre, int estado) {
    //voy a pasar todos los parametros que quiera mostrar (en este caso: id, nombre y estado)
}
