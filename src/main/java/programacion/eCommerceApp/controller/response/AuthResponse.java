package programacion.eCommerceApp.controller.response;

import programacion.eCommerceApp.model.Rol;

public record AuthResponse(String jwt, Integer id, String nombre, String contrasenia, String rol, Integer estado) {

    public AuthResponse(String jwtToken, Integer id2, String nombre2, String contrasenia2, Rol rol2, Integer estado2) {
        this(jwtToken, id2, nombre2, contrasenia2, rol2.name(), estado2);
    }
}
