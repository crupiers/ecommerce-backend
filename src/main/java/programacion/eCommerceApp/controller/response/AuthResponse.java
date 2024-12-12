package programacion.eCommerceApp.controller.response;

public record AuthResponse(String jwt, Integer id, String nombre, String contrasenia, String rol, Integer estado) {
}
