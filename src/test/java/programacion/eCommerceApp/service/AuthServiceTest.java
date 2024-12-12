
package programacion.eCommerceApp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;

import programacion.eCommerceApp.controller.request.NewRegisterRequest;
import programacion.eCommerceApp.controller.response.AuthResponse;
import programacion.eCommerceApp.model.Usuario;
import programacion.eCommerceApp.repository.IUsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AuthService authService;
    private IUsuarioRepository usuarioRepository;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(IUsuarioRepository.class);
        jwtService = mock(JwtService.class);
        authenticationManager = mock(AuthenticationManager.class);
        authService = new AuthService();
        authService.usuarioRepository = usuarioRepository;
        authService.jwtService = jwtService;
        authService.authenticationManager = authenticationManager;
    }

    @Test
    void testRegister_Success() {
        NewRegisterRequest request = new NewRegisterRequest("newUser", "Password123");
        when(usuarioRepository.findByNombre(request.nombre())).thenReturn(Optional.empty());
        when(jwtService.getToken(any(Usuario.class))).thenReturn("jwtToken");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("newUser", response.getNombre());
        assertEquals("jwtToken", response.getToken());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testRegister_UsernameAlreadyExists() {
        NewRegisterRequest request = new NewRegisterRequest("existingUser", "Password123");
        when(usuarioRepository.findByNombre(request.nombre())).thenReturn(Optional.of(new Usuario()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register(request);
        });

        assertEquals("EL NOMBRE DE USUARIO 'existingUser' YA EXISTE", exception.getMessage());
    }

    @Test
    void testRegister_InvalidPassword() {
        NewRegisterRequest request = new NewRegisterRequest("newUser", "pass");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register(request);
        });

        assertEquals("La contraseña del usuario debe tener al menos una mayúscula, una minúscula, un número y no debe tener espacios", exception.getMessage());
    }
}