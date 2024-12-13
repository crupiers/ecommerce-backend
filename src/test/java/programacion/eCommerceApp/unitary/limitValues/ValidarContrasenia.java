package programacion.eCommerceApp.unitary.limitValues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;

import programacion.eCommerceApp.controller.request.NewLoginRequest;
import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.mapper.UsuarioMapper;
import programacion.eCommerceApp.model.Usuario;
import programacion.eCommerceApp.repository.IUsuarioRepository;
import programacion.eCommerceApp.service.AuthService;
import programacion.eCommerceApp.service.JwtService;

// Feature: Validar la longitud de la contraseña (8 a 64 caracteres)
@ExtendWith(MockitoExtension.class)
public class ValidarContrasenia {

    @Mock
    IUsuarioRepository usuarioRepository;
    @Mock
    JwtService jwtService;
    @InjectMocks
    AuthService authService;

    private NewLoginRequest baseRequest;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        NewLoginRequest baseRequest = new NewLoginRequest(
            "testUser",
            "testPassword");
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre(baseRequest.nombre());
        usuario.setContrasenia(baseRequest.contrasenia());
    }

    @Test
    public void contraseniaLongitudValida() {
        
        usuario.setContrasenia("12345678");
        when(usuarioRepository.findByNombre(baseRequest.nombre())).thenReturn(Optional.empty());
    
        when(usuarioRepository.save(any())).thenReturn(usuario);
        
        when(jwtService.getToken(any(Usuario.class))).thenReturn("fake-jwt-token");

        Usuario usuarioGuardado = authService.register(baseRequest);
        Usuario usuario = UsuarioMapper.toEntity(newRegisterRequest, passwordEncoder);
        assertEquals(usuario, usuarioGuardado);
    }

    @Test
    public void contraseniaLongitudInvalida() {
        Usuario usuario = new Usuario();
        usuario.setContrasenia("1234567");
        when(usuarioRepository.save(any())).thenReturn(usuario);

        assertThrows(IllegalArgumentException.class, () -> authService.registrar(usuario));
        verify(usuarioRepository, never()).save(any());
    }
}