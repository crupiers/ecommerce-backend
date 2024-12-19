// package programacion.eCommerceApp.unitary.incompletos;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import programacion.eCommerceApp.service.AuthService;
// import programacion.eCommerceApp.controller.AuthController;
// import programacion.eCommerceApp.controller.request.NewRegisterRequest;
// import programacion.eCommerceApp.controller.response.AuthResponse;
// import programacion.eCommerceApp.service.IAuthService;

// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.hamcrest.Matchers.is;

// @WebMvcTest(AuthController.class) // Proporciona un entorno aislado para testear el controlador
// public class registerControllerTest {

//     @Autowired
//     private MockMvc mockMvc; // Simula las solicitudes HTTP

//     @MockBean
//     private AuthService authService; // Mock del servicio que el controlador usa

//     @MockBean
//     private IAuthService iAuthService;

//     @Autowired
//     private ObjectMapper objectMapper; // Convierte objetos a JSON y viceversa

//     @Test
//     public void shouldReturnBadRequestForInvalidPassword() throws Exception {
//         // Crear un objeto con una contraseña inválida
//         NewRegisterRequest registerRequest = new NewRegisterRequest("nacho", "a");
//         when(authService.register(registerRequest)).thenThrow(new IllegalArgumentException("LA CONTRASEÑA DEBE TENER ENTRE 8 Y 64 CARACTERES - REQUEST"));

//         // Simular una solicitud POST al endpoint del controlador
//         mockMvc.perform(post("/auth/register") // Ruta del endpoint
//                 .contentType(MediaType.APPLICATION_JSON) // Tipo de contenido de la solicitud
//                 .content(objectMapper.writeValueAsString(registerRequest))) // Convertir el objeto a JSON
//             .andExpect(status().isBadRequest()) // Verificar que el estado HTTP es 400
//             .andExpect(jsonPath("$.details").isNotEmpty()) // Verificar que hay un mensaje de error
//             .andExpect(jsonPath("$.details[0]").value("LA CONTRASEÑA DEBE TENER ENTRE 8 Y 64 CARACTERES - REQUEST"));
//     }

//     @Test
//     public void testRegister() throws Exception {
//         NewRegisterRequest newRegisterRequest = new NewRegisterRequest("testUser", "testPassword");
//         AuthResponse authResponse = new AuthResponse("testJwt", 1, "testUser", "testPassword", "ROLE_USER", 1);

//         Mockito.when(iAuthService.register(Mockito.any(NewRegisterRequest.class))).thenReturn(authResponse);

//         mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/auth/register")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(new ObjectMapper().writeValueAsString(newRegisterRequest)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.jwt", is("testJwt")))
//                 .andExpect(jsonPath("$.id", is(1)))
//                 .andExpect(jsonPath("$.nombre", is("testUser")))
//                 .andExpect(jsonPath("$.contrasenia", is("testPassword")))
//                 .andExpect(jsonPath("$.rol", is("ROLE_USER")))
//                 .andExpect(jsonPath("$.estado", is(1)));
//     }
// }
