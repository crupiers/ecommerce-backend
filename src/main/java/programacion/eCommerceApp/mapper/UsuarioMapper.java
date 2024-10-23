package programacion.eCommerceApp.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
            .rol(Rol.USER)
            .build();
    }

    public static AuthResponse toAuthResponse(String jwt) {
        return new AuthResponse(jwt);
    }

}
