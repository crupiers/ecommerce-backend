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
        UserDetails userDetails = usuarioRepository.findByNombre(newLoginRequest.nombre()).orElseThrow();
        String jwt = jwtService.getToken(userDetails);
        return UsuarioMapper.toAuthResponse(jwt);
    }

    public AuthResponse register(NewRegisterRequest newRegisterRequest) {
        if (newRegisterRequest.contrasenia().length() < 8 || newRegisterRequest.contrasenia().length() > 64) {
            throw new IllegalArgumentException("La contraseña debe tener entre 8 y 64 caracteres");
        }
        Usuario usuario = UsuarioMapper.toEntity(newRegisterRequest, passwordEncoder);
        usuario.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm")));
        usuarioRepository.save(usuario);

        return UsuarioMapper.toAuthResponse(jwtService.getToken(usuario));
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

        if (usuarioOptional.get().getEstado() == Usuario.ELIMINADO) {
            throw new IllegalArgumentException("EL USUARIO CON ID '"+id+"' QUE SE QUIERE ACTUALIZAR ESTÁ ELIMINADO");
        }

        Usuario usuario = usuarioOptional.get();
        usuario.setNombre(model.getNombre());
        usuario.setContrasenia(model.getContrasenia());
        usuarioRepository.save(usuario);

        return UsuarioMapper.toAuthResponse(jwtService.getToken(usuario));
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

}
