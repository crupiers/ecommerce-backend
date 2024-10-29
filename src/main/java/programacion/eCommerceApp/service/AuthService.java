package programacion.eCommerceApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(newLoginRequest.nombre(),newLoginRequest.contrasenia()));
        UserDetails userDetails = usuarioRepository.findByNombre(newLoginRequest.nombre()).orElseThrow();
        String jwt = jwtService.getToken(userDetails);
        return UsuarioMapper.toAuthResponse(jwt);
    }

    public AuthResponse register(NewRegisterRequest newRegisterRequest) {
        Usuario usuario = UsuarioMapper.toEntity(newRegisterRequest, passwordEncoder);
        usuario.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm")));
        usuarioRepository.save(usuario);

        return UsuarioMapper.toAuthResponse(jwtService.getToken(usuario));
    }

}
