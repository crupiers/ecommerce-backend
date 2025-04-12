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

import java.time.LocalDate;

@Sql("/scripts/base/reset_db.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = eCommerceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class RegistrarUsuarioTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUsuarioRepository usuarioRepository;
    NewRegisterRequest usuarioRequest;


    @Test
    public void registrarUsuario_Nombre1Caracter() throws Exception {
        // Given
        String nombre = "J";
        usuarioRequest = new NewRegisterRequest(
                nombre, // nombre
                "Password123", // contrasenia
                LocalDate.of(2000, 1, 1)
        );
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(usuarioRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Assertions.assertThat(usuarioRepository.findById(1)).isNotPresent();
    }

    @Test
    public void registrarUsuario_Nombre2Caracteres() throws Exception {
        // Given
        String nombre = "Jo";
        usuarioRequest = new NewRegisterRequest(
                nombre, // nombre
                "Password123", // contrasenia
                LocalDate.of(2000, 1, 1)
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
    public void registrarUsuario_Nombre3Caracteres() throws Exception {
          // Given
          String nombre = "jua";
          usuarioRequest = new NewRegisterRequest(
                  nombre, // nombre
                  "Password123", // contrasenia
                  LocalDate.of(2000, 1, 1)
          );
          // When
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(usuarioRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        // Then
        Assertions.assertThat(usuarioRepository.findById(1)).isPresent();
    }

    @Test
    public void registrarUsuario_Nombre31Caracteres() throws Exception {
        // Given
        String nombre = "j".repeat(31);
        usuarioRequest = new NewRegisterRequest(
                nombre, // nombre
                "Password123",// contrasenia
                LocalDate.of(2000, 1, 1)
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
    public void registrarUsuario_Nombre32Caracteres() throws Exception {

        // Given
        String nombre = "j".repeat(32);
        usuarioRequest = new NewRegisterRequest(
                nombre, // nombre
                "Password123", // contrasenia
                LocalDate.of(2000, 1, 1)
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
    public void registrarUsuario_Nombre33Caracteres() throws Exception {
        // Given
        String nombre = "j".repeat(33);
        usuarioRequest = new NewRegisterRequest(
                nombre, // nombre
                "Password123", // contrasenia
                LocalDate.of(2000, 1, 1)
        );
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(usuarioRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("EL NOMBRE DEBE IR DE ENTRE 2 Y 32 CARACTERES"));

        // Then
        Assertions.assertThat(usuarioRepository.findById(1)).isNotPresent();    }
}



