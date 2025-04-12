/*package programacion.eCommerceApp.unitary.decisionTables;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import programacion.eCommerceApp.controller.AuthController;
import programacion.eCommerceApp.controller.request.NewRegisterRequest;
import programacion.eCommerceApp.controller.response.AuthResponse;
import programacion.eCommerceApp.service.AuthService;
import programacion.eCommerceApp.service.JwtService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
public class ValidarContraseniaTest {

        @Mock
        AuthService authService;

        @InjectMocks
        private AuthController authController;

        private MockMvc mockMvc;
        private ObjectMapper objectMapper;
        private NewRegisterRequest baseRequest;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this); // Inicializa el controlador y los mocks
            mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
            objectMapper = new ObjectMapper(); // Instancia real del ObjectMapper
        }
        @Test
        void register_usuario_contraseniaValidaLarga() throws Exception {
            // Given
            String contraseniaValida = "Clave12345";

            baseRequest = new NewRegisterRequest("nacho", contraseniaValida);

            //AuthResponse authResponse = new AuthResponse(
                "jwtToken",
                1,
                baseRequest.nombre(),
                baseRequest.contrasenia(),
                "USER_ROLE",
                1
            );

            when(authService.register(any(NewRegisterRequest.class))).thenReturn(authResponse);

            mockMvc.perform(post("/ecommerce/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(baseRequest)))
                    .andExpect(status().isOk());
        }
        @Test
        void register_usuario_contraseniaConEspacio() throws Exception {
        // Given
        String contraseniaInvalida = "A ";

        baseRequest = new NewRegisterRequest("nacho", contraseniaInvalida);

        mockMvc.perform(post("/ecommerce/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(baseRequest)))
                .andExpect(status().isBadRequest());
}

        @Test
        void register_usuario_contraseniaCorta() throws Exception {
            // Given
            String contraseniaInvalida = "As";

            baseRequest = new NewRegisterRequest("nacho", contraseniaInvalida);

            mockMvc.perform(post("/ecommerce/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(baseRequest)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void register_usuario_contraseniaVacia() throws Exception {
            // Given
            String contraseniaInvalida = "";

            baseRequest = new NewRegisterRequest("nacho", contraseniaInvalida);

            mockMvc.perform(post("/ecommerce/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(baseRequest)))
                    .andExpect(status().isBadRequest());
        }
        @Test
        void register_usuario_contraseniaEspacioFinal() throws Exception {
            // Given
            String contraseniaInvalida = "clave12345 ";

            baseRequest = new NewRegisterRequest("nacho", contraseniaInvalida);

            mockMvc.perform(post("/ecommerce/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(baseRequest)))
                    .andExpect(status().isBadRequest());
        }
        @Test
        void register_usuario_contraseniaSoloEspacios() throws Exception {
            // Given
            String contraseniaInvalida = " ";

            baseRequest = new NewRegisterRequest("nacho", contraseniaInvalida);

            mockMvc.perform(post("/ecommerce/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(baseRequest)))
                    .andExpect(status().isBadRequest());
        }

}
*/
