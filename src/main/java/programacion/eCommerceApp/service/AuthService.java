package programacion.eCommerceApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import programacion.eCommerceApp.controller.request.NewLoginRequest;
import programacion.eCommerceApp.controller.request.NewRegisterRequest;
import programacion.eCommerceApp.controller.response.AuthResponse;
import programacion.eCommerceApp.mapper.UsuarioMapper;
import programacion.eCommerceApp.model.Usuario;
import programacion.eCommerceApp.repository.IUsuarioRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthResponse login(NewLoginRequest newLoginRequest) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findByNombre(newLoginRequest.nombre());

        if (usuarioOptional.isPresent() && usuarioOptional.get().getEstado() == Usuario.ELIMINADO) {
            throw new IllegalArgumentException("EL USUARIO '"+usuarioOptional.get().getNombre()+"' ESTÁ ELIMINADO");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(newLoginRequest.nombre(),newLoginRequest.contrasenia()));
        UserDetails userDetails = usuarioRepository.findByNombre(newLoginRequest.nombre()).orElseThrow(
            () -> new IllegalArgumentException("EL USUARIO '"+newLoginRequest.nombre()+"' NO EXISTE")
        );
        String jwt = jwtService.getToken(userDetails);

        Usuario usuario = usuarioOptional.get();
        return UsuarioMapper.toAuthResponse(usuario, jwt);
    }

    public AuthResponse register(NewRegisterRequest newRegisterRequest) {
        if (usuarioRepository.findByNombre(newRegisterRequest.nombre()).isPresent()) {
            throw new IllegalArgumentException("EL NOMBRE DE USUARIO '" + newRegisterRequest.nombre() + "' YA EXISTE");
        }
        if (!newRegisterRequest.contrasenia().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*\\s).{8,64}$")) {
            throw new IllegalArgumentException("La contraseña del usuario debe tener al menos una mayúscula, una minúscula, un número y no debe tener espacios");
        }

        Usuario usuario = UsuarioMapper.toEntity(newRegisterRequest, passwordEncoder);
        usuario.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm")));
        usuarioRepository.save(usuario);
        String jwt = jwtService.getToken(usuario);
        return UsuarioMapper.toAuthResponse(usuario, jwt);
    }

    public AuthResponse actualizar(NewRegisterRequest newRegisterRequest, Integer id) {
        if (newRegisterRequest.contrasenia().length() < 8 || newRegisterRequest.contrasenia().length() > 64) {
            throw new IllegalArgumentException("La contraseña debe tener entre 8 y 64 caracteres");
        }
        Usuario model = UsuarioMapper.toEntity(newRegisterRequest, passwordEncoder);
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isEmpty()) {
            throw new IllegalArgumentException("EL USUARIO CON ID '"+id+"' QUE SE QUIERE ACTUALIZAR NO EXISTE");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!Objects.equals(authentication.getName(), usuarioOptional.get().getNombre())) {
            throw new IllegalArgumentException("EL USUARIO '"+authentication.getName()+"' ESTÁ INTENTANDO ACTUALIZAR UN USUARIO DE ID '"+id+"' QUE NO ES EL SUYO");
        }

        if (usuarioRepository.findByNombre(newRegisterRequest.nombre()).isPresent()) {
            throw new IllegalArgumentException("EL NOMBRE DE USUARIO '"+newRegisterRequest.nombre()+"' YA EXISTE");
        }

        if (usuarioOptional.get().getEstado() == Usuario.ELIMINADO) {
            throw new IllegalArgumentException("EL USUARIO CON ID '"+id+"' QUE SE QUIERE ACTUALIZAR ESTÁ ELIMINADO");
        }

        Usuario usuario = usuarioOptional.get();
        usuario.setNombre(model.getNombre());
        usuario.setContrasenia(model.getContrasenia());
        usuarioRepository.save(usuario);
        String jwt = jwtService.getToken(usuario);
        return UsuarioMapper.toAuthResponse(usuario, jwt);
    }

    public void eliminar(Integer id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isEmpty()) {
            throw new IllegalArgumentException("EL USUARIO '"+id+"' NO EXISTE Y NO PUEDE SER BORRADO");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!Objects.equals(authentication.getName(), usuarioOptional.get().getNombre())) {
            throw new IllegalArgumentException("EL USUARIO '"+authentication.getName()+"' ESTÁ INTENTANDO ELIMINAR UN USUARIO DE ID '"+id+"' QUE NO ES EL SUYO");
        }

        if (usuarioOptional.get().getEstado() == Usuario.ELIMINADO) {
            throw new IllegalArgumentException("EL USUARIO CON ID '"+id+"' YA ESTÁ ELIMINADO");
        }

        Usuario usuario = usuarioOptional.get();
        usuario.eliminar();
        usuarioRepository.save(usuario);
    }

    public AuthResponse buscarPorId(Integer id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isEmpty()) {
            throw new IllegalArgumentException("EL USUARIO CON ID '"+id+"' NO EXISTE");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!Objects.equals(authentication.getName(), usuarioOptional.get().getNombre())) {
            throw new IllegalArgumentException("EL USUARIO '"+authentication.getName()+"' ESTÁ INTENTANDO BUSCAR UN USUARIO DE ID '"+id+"' QUE NO ES EL SUYO");
        }

        if (usuarioOptional.get().getEstado() == Usuario.ELIMINADO) {
            throw new IllegalArgumentException("EL USUARIO CON ID '" + id + "' ESTÁ ELIMINADO");
        }

        String jwt = jwtService.getToken(usuarioOptional.get());
        Usuario usuario = usuarioOptional.get();
        return UsuarioMapper.toAuthResponse(usuario, jwt);
    }
}
