package programacion.eCommerceApp.integration.limitValues;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import programacion.eCommerceApp.integration.Base.BaseIntegrationTest;
import programacion.eCommerceApp.repository.IUsuarioRepository;
import programacion.eCommerceApp.eCommerceApplication;
import programacion.eCommerceApp.controller.request.NewRegisterRequest;

@Sql("/scripts/base/reset_db.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = eCommerceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class RegistrarUsuarioCaracterTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUsuarioRepository usuarioRepository;
    NewRegisterRequest usuarioRequest;

    @Test
    public void registrarUsuario_NombreSoloMinusculas() throws Exception {
        // Given
        String nombre = "juan";
        usuarioRequest = new NewRegisterRequest(
                nombre, // nombre
                "Password123" // contrasenia
        );
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(usuarioRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        // Then
        Assertions.assertThat(usuarioRepository.findById(1)).isPresent();
    }

    @Test
    public void registrarUsuario_NombreConMayusculas() throws Exception {
        // Given
        String nombre = "Juan";
        usuarioRequest = new NewRegisterRequest(
                nombre, // nombre
                "Password123" // contrasenia
        );
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(usuarioRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("EL NOMBRE DEBE ESTAR CONFORMADO POR PALABRAS DE SÓLO LETRAS MINÚSCULAS O DE SÓLO NÚMEROS, LAS PALABRAS DEBEN ESTAR SEPARADAS CON GUIÓN COMÚN (-)"));

        // Then
        Assertions.assertThat(usuarioRepository.findById(1)).isNotPresent();
    }

    @Test
    public void registrarUsuario_NombreSoloNumeros() throws Exception {
        // Given
        String nombre = "12345";
        usuarioRequest = new NewRegisterRequest(
                nombre, // nombre
                "Password123" // contrasenia
        );
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(usuarioRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        // Then
        Assertions.assertThat(usuarioRepository.findById(1)).isPresent();
    }

    @Test
    public void registrarUsuario_NombreConEspacios() throws Exception {
        // Given
        String nombre = "juan perez";
        usuarioRequest = new NewRegisterRequest(
                nombre, // nombre
                "Password123" // contrasenia
        );
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(usuarioRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("EL NOMBRE DEBE ESTAR CONFORMADO POR PALABRAS DE SÓLO LETRAS MINÚSCULAS O DE SÓLO NÚMEROS, LAS PALABRAS DEBEN ESTAR SEPARADAS CON GUIÓN COMÚN (-)"));

        // Then
        Assertions.assertThat(usuarioRepository.findById(1)).isNotPresent();
    }

    @Test
    public void registrarUsuario_NombreNuloOVacio() throws Exception {
        // Given
        String nombre = "";
        usuarioRequest = new NewRegisterRequest(
                nombre, // nombre
                "Password123" // contrasenia
        );
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(usuarioRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("EL NOMBRE DEBE IR DE ENTRE 2 Y 32 CARACTERES"));

        // Then
        Assertions.assertThat(usuarioRepository.findById(1)).isNotPresent();
    }
}
