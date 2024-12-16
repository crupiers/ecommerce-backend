package programacion.eCommerceApp.unitary.incompletos;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import programacion.eCommerceApp.controller.request.NewRegisterRequest;
import programacion.eCommerceApp.model.Usuario;
import programacion.eCommerceApp.repository.IUsuarioRepository;
import programacion.eCommerceApp.service.AuthService;
import programacion.eCommerceApp.service.JwtService;

@ExtendWith(MockitoExtension.class)
public class ValidarContrasenia {

    @Mock
    IUsuarioRepository usuarioRepository;

    @Mock
     JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private NewRegisterRequest baseRequest;
    private Usuario usuario;
    private String contraseniaValida = "Password";
    

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configuración inicial
        baseRequest = new NewRegisterRequest(
            "nuevoUsuario",
            "Password123"
        );

        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre(baseRequest.nombre());
        usuario.setContrasenia("encodedPassword");
    }

    @Test
    public void register_usuario_constraseñaValida() {
        // Given
        

        baseRequest = new NewRegisterRequest(
        baseRequest.nombre(),
        contraseniaValida
        );
        // Configura el comportamiento de los mocks
        when(passwordEncoder.encode(baseRequest.contrasenia())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any())).thenReturn(usuario);
        when(jwtService.getToken(any(Usuario.class))).thenReturn("fake-jwt-token");
        // Verifica que no se lanzaron excepciones
        assertDoesNotThrow(() -> authService.register(baseRequest));
}
@Test
void register_contraseniaInvalida_lanzarExcepcion() {
    // Caso 1: Contraseña sin mayúsculas
    String contraseniaInvalidaSinMayusucula = "P";
    NewRegisterRequest registerRequest = new NewRegisterRequest(
        "nacho",
        contraseniaInvalidaSinMayusucula
    );

    // Verifica que lanza la excepción con el mensaje esperado
    assertThrows(IllegalArgumentException.class,() -> authService.register(registerRequest));
    
}

}
