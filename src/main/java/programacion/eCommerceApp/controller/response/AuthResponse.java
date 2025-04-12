package programacion.eCommerceApp.controller.response;

import java.time.LocalDate;

public record AuthResponse(String jwt, Integer id, String nombre, String contrasenia, String rol, Integer estado, LocalDate fechaNacimiento) {
}
