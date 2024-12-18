package programacion.eCommerceApp.unitary.incompletos;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import programacion.eCommerceApp.service.AuthService;
import programacion.eCommerceApp.service.JwtService;
import programacion.eCommerceApp.controller.request.NewRegisterRequest;
import programacion.eCommerceApp.controller.response.AuthResponse;
import programacion.eCommerceApp.model.Rol;
import programacion.eCommerceApp.model.Usuario;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest2 {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private ObjectMapper objectMapper;

    @Test
    public void registerValidPassword() throws Exception {
        // Datos de entrada válidos
        NewRegisterRequest registerRequest = new NewRegisterRequest("nuevoUsuario", "Password123");

        // Crear una respuesta de ejemplo para el registro
        Usuario usuario = Usuario.builder()
                .id(1)
                .nombre("nuevoUsuario")
                .contrasenia("encodedPassword")
                .rol(Rol.ROLE_USER)
                .estado(Usuario.COMUN)
                .build();

        String jwtToken = "sampleJwtToken"; // Un JWT de ejemplo para el test
        AuthResponse authResponse = new AuthResponse(jwtToken, usuario.getId(), usuario.getNombre(),usuario.getContrasenia(), usuario.getRol(), usuario.getEstado()); 

        // Configurar el mock para que authService registre el usuario y devuelva el AuthResponse
        when(authService.register(registerRequest)).thenReturn(authResponse);

        // Realizar el test del controlador
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk()) // Verificar que el código de estado sea 200 OK
                .andExpect(jsonPath("$.usuario.nombre").value("nuevoUsuario")) // Verificar que el nombre del usuario sea correcto
                .andExpect(jsonPath("$.jwt").value(jwtToken)); // Verificar que el JWT sea el esperado
    }
}
