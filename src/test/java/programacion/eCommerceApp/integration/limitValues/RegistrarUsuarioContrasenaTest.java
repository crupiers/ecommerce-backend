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
public class RegistrarUsuarioContrasenaTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUsuarioRepository usuarioRepository;
    NewRegisterRequest usuarioRequest;

    @Test
    public void registrarContrasenia_Valida() throws Exception {
        // Given
        String contrasenia = "Juan12345";
        usuarioRequest = new NewRegisterRequest(
                "usuario", // nombre
                contrasenia,
                LocalDate.of(2000, 1, 1)// contrasenia
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
    public void registrarContrasenia_SinMinusculas() throws Exception {
        // Given
        String contrasenia = "JUAN12345";
        usuarioRequest = new NewRegisterRequest(
                "usuario", // nombre
                contrasenia,
                LocalDate.of(2000, 1, 1)// contrasenia
        );
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(usuarioRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("LA CONTRASEÑA DEBE CONTENER AL MENOS UNA MINÚSCULA, UNA MAYÚSCULA Y UN NÚMERO, NO PUEDE CONTENER ESPACIOS"));

        // Then
        Assertions.assertThat(usuarioRepository.findById(1)).isNotPresent();
    }

    @Test
    public void registrarContrasenia_ConEspacio() throws Exception {
        // Given
        String contrasenia = "juan12345 ";
        usuarioRequest = new NewRegisterRequest(
                "usuario", // nombre
                contrasenia,
                LocalDate.of(2000, 1, 1)// contrasenia
        );
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(usuarioRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("LA CONTRASEÑA DEBE CONTENER AL MENOS UNA MINÚSCULA, UNA MAYÚSCULA Y UN NÚMERO, NO PUEDE CONTENER ESPACIOS"));

        // Then
        Assertions.assertThat(usuarioRepository.findById(1)).isNotPresent();
    }

    @Test
    public void registrarContraseina_SinMayusculas() throws Exception {
        // Given
        String contrasenia = "juan12345";
        usuarioRequest = new NewRegisterRequest(
                "usuario", // nombre
                contrasenia,
                LocalDate.of(2000, 1, 1)// contrasenia
        );
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(usuarioRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("LA CONTRASEÑA DEBE CONTENER AL MENOS UNA MINÚSCULA, UNA MAYÚSCULA Y UN NÚMERO, NO PUEDE CONTENER ESPACIOS"));

        // Then
        Assertions.assertThat(usuarioRepository.findById(1)).isNotPresent();
    }

    @Test
    public void registrarContrasenia_SinNumeros() throws Exception {
        // Given
        String contrasenia = "JuanPerez";
        usuarioRequest = new NewRegisterRequest(
                "usuario", // nombre
                contrasenia,
                LocalDate.of(2000, 1, 1)// contrasenia
        );
        
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(usuarioRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("LA CONTRASEÑA DEBE CONTENER AL MENOS UNA MINÚSCULA, UNA MAYÚSCULA Y UN NÚMERO, NO PUEDE CONTENER ESPACIOS"));

        // Then
        Assertions.assertThat(usuarioRepository.findById(1)).isNotPresent();
    }
}
