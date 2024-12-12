package programacion.eCommerceApp.unitary.equivalentPartition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import programacion.eCommerceApp.model.Usuario;
import programacion.eCommerceApp.repository.IUsuarioRepository;
import programacion.eCommerceApp.service.AuthService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void eliminar_usuarioNoExiste_lanzaExcepcion() {
        // Arrange
        Integer userId = 1;
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> authService.eliminar(userId));
        assertEquals("EL USUARIO '1' NO EXISTE Y NO PUEDE SER BORRADO", exception.getMessage());
    }

    @Test
    void eliminar_usuarioNoAutenticado_lanzaExcepcion() {
        // Arrange
        Integer userId = 1;
        Usuario usuario = new Usuario();
        usuario.setNombre("otroUsuario");
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuarioActual");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> authService.eliminar(userId));
        assertEquals("EL USUARIO 'usuarioActual' ESTÁ INTENTANDO ELIMINAR UN USUARIO DE ID '1' QUE NO ES EL SUYO", exception.getMessage());
    }

    @Test
    void eliminar_usuarioYaEliminado_lanzaExcepcion() {
        // Arrange
        Integer userId = 1;
        Usuario usuario = new Usuario();
        usuario.setEstado(Usuario.ELIMINADO);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuarioActual");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> authService.eliminar(userId));
        assertEquals("EL USUARIO CON ID '1' YA ESTÁ ELIMINADO", exception.getMessage());
    }

    @Test
    void eliminar_usuarioValido_eliminaCorrectamente() {
        // Arrange
        Integer userId = 1;
        Usuario usuario = mock(Usuario.class);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuarioActual");
        when(usuario.getNombre()).thenReturn("usuarioActual");
        when(usuario.getEstado()).thenReturn(Usuario.COMUN);

        // Act
        authService.eliminar(userId);

        // Assert
        verify(usuario).eliminar();
        verify(usuarioRepository).save(usuario);
    }
}
