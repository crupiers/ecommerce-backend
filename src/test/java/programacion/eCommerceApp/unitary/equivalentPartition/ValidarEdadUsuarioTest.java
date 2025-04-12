package programacion.eCommerceApp.unitary.equivalentPartition;


import io.jsonwebtoken.Jwt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import programacion.eCommerceApp.controller.request.NewRegisterRequest;
import programacion.eCommerceApp.repository.IUsuarioRepository;
import programacion.eCommerceApp.service.AuthService;
import programacion.eCommerceApp.service.JwtService;
import java.time.LocalDate;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


/*verifico:
-edad < 18
-edad = 18
-edad entre 18 y 110
-edad > 110
 */
@ExtendWith(MockitoExtension.class)
public class ValidarEdadUsuarioTest {
    @Mock
    private IUsuarioRepository iUsuarioRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private NewRegisterRequest newRegisterRequest;
    @BeforeEach
    public void setup(){
        newRegisterRequest = new NewRegisterRequest("agustin",
                "Contrasenia1234",
                LocalDate.of(2002,8,11));
    }

    @Test
    public void EdadMenorA18() { //17
        NewRegisterRequest request = new NewRegisterRequest(
                newRegisterRequest.nombre(),
                newRegisterRequest.contrasenia(),
                LocalDate.now().minusYears(17)
        );
        assertThrows(IllegalArgumentException.class, () -> {
            authService.register(request);
        });
    }

    @Test
    public void EdadIgualA18() { //18
        NewRegisterRequest request = new NewRegisterRequest(
                newRegisterRequest.nombre(),
                newRegisterRequest.contrasenia(),
                LocalDate.now().minusYears(18)
        );
        // Este test pasa si NO se lanza ninguna excepción
        assertDoesNotThrow(() -> {
            authService.register(request);
        });
    }

    @Test
    public void EdadEntre18Y110() {
        NewRegisterRequest request = new NewRegisterRequest(
                newRegisterRequest.nombre(),
                newRegisterRequest.contrasenia(),
                LocalDate.now().minusYears(40)
        );

        assertDoesNotThrow(() -> {
            authService.register(request);
        });
    }

    @Test
    public void EdadMayorA110() {
        NewRegisterRequest request = new NewRegisterRequest(
                newRegisterRequest.nombre(),
                newRegisterRequest.contrasenia(),
                LocalDate.now().minusYears(115)
        );
        assertThrows(IllegalArgumentException.class, () -> {
            authService.register(request);
        });
    }


}
