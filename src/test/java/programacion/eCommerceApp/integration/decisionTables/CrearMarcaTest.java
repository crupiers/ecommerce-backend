package programacion.eCommerceApp.integration.decisionTables;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import programacion.eCommerceApp.eCommerceApplication;
import programacion.eCommerceApp.controller.request.NewMarcaRequest;
import programacion.eCommerceApp.integration.Base.BaseIntegrationTest;
import programacion.eCommerceApp.repository.IMarcaRepository;

@ActiveProfiles("test")
@Sql("/scripts/base/reset_db.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = eCommerceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class CrearMarcaTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IMarcaRepository brandRepository;

    @Test
    public void registrarMarca_NombreDescripcionValidos() throws Exception {
        // Given
        NewMarcaRequest brandRequest = new NewMarcaRequest("TCL", "-");
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/admin/marcas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(brandRequest)))
        // Then
            .andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertThat(brandRepository.findById(1)).isPresent();
    }

    @Test
    public void registrarMarca_DescripcionInvalida() throws Exception {
        // Given
        NewMarcaRequest brandRequest = new NewMarcaRequest("TCL", "");
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/admin/marcas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(brandRequest)))
        // Then
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("LA DESCRIPCIÓN NO TIENE QUE HABER ESPACIOS DOBLES NI ESPACIO AL INICIO NI AL FINAL, NI PUEDE ESTAR VACÍO"));
        Assertions.assertThat(brandRepository.findById(1)).isNotPresent();
    }

    @Test
    public void registrarMarca_NombreInvalido() throws Exception {
        // Given
        NewMarcaRequest brandRequest = new NewMarcaRequest("", "-");
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/admin/marcas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(brandRequest)))
        // Then
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("EL NOMBRE DEBE IR DE ENTRE 2 Y 32 CARACTERES"));
        Assertions.assertThat(brandRepository.findById(1)).isNotPresent();
    }

    @Test
    public void registrarMarca_NombreDescripcionInvalidos() throws Exception {
        // Given
        NewMarcaRequest brandRequest = new NewMarcaRequest("", "");
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/admin/marcas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(brandRequest)))
        // Then
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("EL NOMBRE DEBE IR DE ENTRE 2 Y 32 CARACTERES"));
        Assertions.assertThat(brandRepository.findById(1)).isNotPresent();
    }
}
