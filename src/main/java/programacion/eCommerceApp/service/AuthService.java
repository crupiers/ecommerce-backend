package programacion.eCommerceApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
import programacion.eCommerceApp.model.Rol;
import programacion.eCommerceApp.model.Usuario;
import programacion.eCommerceApp.repository.IUsuarioRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
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

        Usuario usuario = usuarioOptional.get();

        if(usuario.getEstado() == Usuario.ELIMINADO) {
            throw new IllegalArgumentException("EL USUARIO '" + usuario.getNombre() + "' ESTÁ ELIMINADO");
        }

        if (usuario.isBloqueado()) {
            if (usuario.getUltimoIntentoFallido() != null &&
                    usuario.getUltimoIntentoFallido().isBefore(LocalDateTime.now().minusMinutes(2))) {

                usuario.setIntentoFallidos(0);
                usuario.setBloqueado(false);
                usuarioRepository.save(usuario); // Se desbloquea automáticamente
            } else {
                throw new IllegalArgumentException("CUENTA BLOQUEADA TEMPORALMENTE. INTENTE MÁS TARDE.");
            }
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(newLoginRequest.nombre(), newLoginRequest.contrasenia())
            );
        } catch (BadCredentialsException ex) {
            // Acá manejás el intento fallido
            usuario.setIntentoFallidos(usuario.getIntentoFallidos() + 1);
            usuario.setUltimoIntentoFallido(LocalDateTime.now());

            if (usuario.getIntentoFallidos() >= 5) {
                usuario.setBloqueado(true);
            }

            usuarioRepository.save(usuario); // Importante: guardás los cambios

            throw new IllegalArgumentException("CREDENCIALES INCORRECTAS");
        }

        usuario.setIntentoFallidos(0);
        usuario.setBloqueado(false);
        usuarioRepository.save(usuario);

        UserDetails userDetails = usuarioRepository.findByNombre(newLoginRequest.nombre()).orElseThrow(
                () -> new IllegalArgumentException("EL USUARIO '" + newLoginRequest.nombre() + "' NO EXISTE")
        );
        String jwt = jwtService.getToken(userDetails);

        return UsuarioMapper.toAuthResponse(usuario, jwt);
    }

    public AuthResponse register(NewRegisterRequest newRegisterRequest) {
        // Verificar si el nombre cumple con los requisitos
        if (usuarioRepository.findByNombre(newRegisterRequest.nombre()).isPresent()) {
            throw new IllegalArgumentException("EL NOMBRE DE USUARIO '" + newRegisterRequest.nombre() + "' YA EXISTE");
        }

        //verifico que sea mayor de edad >= 18
        if(!esMayorDeEdad(newRegisterRequest.fechaNacimiento())){
            throw new IllegalArgumentException("EL USUARIO DEBE SER MAYOR DE EDAD");
        }
        
        Usuario usuario = UsuarioMapper.toEntity(newRegisterRequest, passwordEncoder);

        usuario.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm")));

        if (usuario.getNombre().equals("admin")) {
            usuario.setRol(Rol.ROLE_ADMIN);
        }

        usuarioRepository.save(usuario);
        String jwt = jwtService.getToken(usuario);
        return UsuarioMapper.toAuthResponse(usuario, jwt);
    }

    public AuthResponse actualizar(NewRegisterRequest newRegisterRequest, Integer id) {

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
            throw new IllegalArgumentException("EL NOMBRE DE USUARIO '"+newRegisterRequest.nombre()+"' YA EXsISTE");
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

    private boolean esMayorDeEdad(LocalDate fechaNacimiento) {
        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        return edad >= 18 && edad <=110;
    }
}
