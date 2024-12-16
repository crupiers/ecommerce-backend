
package programacion.eCommerceApp.unitary.incompletos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import programacion.eCommerceApp.controller.request.NewRegisterRequest;
import programacion.eCommerceApp.controller.response.AuthResponse;
import programacion.eCommerceApp.mapper.UsuarioMapper;
import programacion.eCommerceApp.model.Usuario;
import programacion.eCommerceApp.repository.IUsuarioRepository;
import programacion.eCommerceApp.service.AuthService;
import programacion.eCommerceApp.service.JwtService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private IUsuarioRepository usuarioRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthService authService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        NewRegisterRequest newRegisterRequest = new NewRegisterRequest("testUser", "testPassword");
        Usuario usuario = UsuarioMapper.toEntity(newRegisterRequest, passwordEncoder);
        usuario.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm")));

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(jwtService.getToken(any(Usuario.class))).thenReturn("testToken");

        AuthResponse response = authService.register(newRegisterRequest);

        assertNotNull(response);
        assertEquals("testToken", response.token());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        verify(jwtService, times(1)).getToken(any(Usuario.class));
    }

    @Test
    void testRegisterWithShortPassword() {
        NewRegisterRequest newRegisterRequest = new NewRegisterRequest("testUser", "short");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register(newRegisterRequest);
        });

        assertEquals("La contraseña debe tener entre 8 y 64 caracteres", exception.getMessage());
    }

    @Test
    void testRegisterWithLongPassword() {
        String longPassword = "a".repeat(65);
        NewRegisterRequest newRegisterRequest = new NewRegisterRequest("testUser", longPassword);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register(newRegisterRequest);
        });

        assertEquals("La contraseña debe tener entre 8 y 64 caracteres", exception.getMessage());
    }
}