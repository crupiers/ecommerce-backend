package programacion.eCommerceApp.controller.response;

public record AuthResponse(String jwt, Integer id, String nombre, String contrasenia, String rol, Integer estado) {
    public AuthResponse {
        this.jwt = jwt;
        this.id = id;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.estado = estado;
    }
}