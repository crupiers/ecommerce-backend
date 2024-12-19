// package programacion.eCommerceApp.unitary.incompletos;


// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.extension.ExtendWith;

// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.mockito.junit.jupiter.MockitoExtension;
// import programacion.eCommerceApp.repository.IUsuarioRepository;
// import programacion.eCommerceApp.service.AuthService;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContext;
// import org.springframework.security.core.context.SecurityContextHolder;
// import programacion.eCommerceApp.model.Usuario;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.*;
// @ExtendWith(MockitoExtension.class)
// public class RegistrarUsuarioTest {

//     @InjectMocks
//     private AuthService authService;

//     @Mock
//     private IUsuarioRepository usuarioRepository;

//     @Mock
//     private SecurityContext securityContext;

//     @Mock
//     private Authentication authentication;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//         SecurityContextHolder.setContext(securityContext);
//         when(securityContext.getAuthentication()).thenReturn(authentication);
//     }

//     @Test
//     public void eliminar_usuarioNoExiste_lanzaExcepcion() {
//         Integer id = 1;
//         when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

//         assertThrows(IllegalArgumentException.class, () -> authService.eliminar(id));
//     }

//     @Test
//     public void eliminar_usuarioNoAutenticadoIntentandoEliminarOtro_lanzaExcepcion() {
//         Integer id = 1;
//         Usuario usuario = new Usuario();
//         usuario.setId(id);
//         usuario.setNombre("otroUsuario");
//         when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
//         when(authentication.getName()).thenReturn("usuarioDiferente");

//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> authService.eliminar(id));
//         assertEquals("EL USUARIO 'usuarioDiferente' ESTÁ INTENTANDO ELIMINAR UN USUARIO DE ID '1' QUE NO ES EL SUYO", exception.getMessage());
//     }

//     @Test
//     public void eliminar_usuarioYaEliminado_lanzaExcepcion() {
//         Integer id = 1;
//         Usuario usuario = new Usuario();
//         usuario.setId(id);
//         usuario.setNombre("usuario");
//         usuario.setEstado(Usuario.ELIMINADO);
//         when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
//         when(authentication.getName()).thenReturn("usuario");

//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> authService.eliminar(id));
//         assertEquals("EL USUARIO CON ID '1' YA ESTÁ ELIMINADO", exception.getMessage());
//     }

//     @Test
//     public void eliminar_usuarioCorrecto_actualizaEstadoYGuarda() {
//         Integer id = 1;
//         Usuario usuario = new Usuario();
//         usuario.setId(id);
//         usuario.setNombre("usuario");
//         usuario.setEstado(Usuario.COMUN);
//         when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
//         when(authentication.getName()).thenReturn("usuario");

//         authService.eliminar(id);

//         assertEquals(Usuario.ELIMINADO, usuario.getEstado());
//         verify(usuarioRepository).save(usuario);
//     }
// }
