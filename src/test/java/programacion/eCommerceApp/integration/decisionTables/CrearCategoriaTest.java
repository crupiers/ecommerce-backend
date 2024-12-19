package programacion.eCommerceApp.integration.decisionTables;

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
import programacion.eCommerceApp.eCommerceApplication;
import programacion.eCommerceApp.controller.request.NewCategoriaRequest;
import programacion.eCommerceApp.integration.Base.BaseIntegrationTest;
import programacion.eCommerceApp.repository.ICategoriaRepository;

@Sql("/scripts/base/reset_db.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = eCommerceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class CrearCategoriaTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ICategoriaRepository categoryRepository;

    @Test
    public void registrarCategoria_NombreDescripcionValidos() throws Exception {
        // Given
        NewCategoriaRequest categoryRequest = new NewCategoriaRequest("Limpieza", "-");
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/admin/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(categoryRequest)))
        // Then
            .andExpect(MockMvcResultMatchers.status().isCreated());
        Assertions.assertThat(categoryRepository.findById(1)).isPresent();
    }

    @Test
    public void registrarCategoria_DescripcionInvalida() throws Exception {
        // Given
        NewCategoriaRequest categoryRequest = new NewCategoriaRequest("Limpieza", "");
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/admin/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(categoryRequest)))
        // Then
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("LA DESCRIPCIÓN NO TIENE QUE HABER ESPACIOS DOBLES NI ESPACIO AL INICIO NI AL FINAL, NI PUEDE ESTAR VACÍO"));
        Assertions.assertThat(categoryRepository.findById(1)).isNotPresent();
    }

    @Test
    public void registrarCategoria_NombreInvalido() throws Exception {
        // Given
        NewCategoriaRequest categoryRequest = new NewCategoriaRequest("", "-");
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/admin/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(categoryRequest)))
        // Then
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("SÓLO PUEDEN HABER LETRAS Y NÚMEROS, NO TIENE QUE HABER ESPACIOS DOBLES NI ESPACIO AL INICIO NI AL FINAL"));
        Assertions.assertThat(categoryRepository.findById(1)).isNotPresent();
    }

    @Test
    public void registrarCategoria_NombreDescripcionInvalidos() throws Exception {
        // Given
        NewCategoriaRequest categoryRequest = new NewCategoriaRequest("", "");
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/admin/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(categoryRequest)))
        // Then
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertThat(categoryRepository.findById(1)).isNotPresent();
    }
}
