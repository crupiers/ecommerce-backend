package programacion.eCommerceApp.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import programacion.eCommerceApp.controller.request.NewRegisterRequest;
import programacion.eCommerceApp.controller.response.AuthResponse;
import programacion.eCommerceApp.model.Rol;
import programacion.eCommerceApp.model.Usuario;



public class UsuarioMapper {

    public static Usuario toEntity(NewRegisterRequest newRegisterRequest, PasswordEncoder passwordEncoder) {
        return Usuario.builder()
            .nombre(newRegisterRequest.nombre())
            .contrasenia(passwordEncoder.encode(newRegisterRequest.contrasenia()))
                .fechaNacimiento(newRegisterRequest.fechaNacimiento())
            .rol(Rol.ROLE_USER)
            .build();
    }

    public static AuthResponse toAuthResponse(Usuario usuario, String jwt) {
        return new AuthResponse(jwt, usuario.getId(), usuario.getNombre(), usuario.getContrasenia(), usuario.getRol().name(), usuario.getEstado(), usuario.getFechaNacimiento());
    }

}
