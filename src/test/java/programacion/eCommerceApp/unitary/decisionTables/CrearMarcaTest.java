package programacion.eCommerceApp.unitary.decisionTables;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import programacion.eCommerceApp.controller.MarcaController;
import programacion.eCommerceApp.controller.request.NewMarcaRequest;
import programacion.eCommerceApp.service.MarcaService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ActiveProfiles("test")
public class CrearMarcaTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private MarcaService marcaService;
    @InjectMocks
    private MarcaController marcaController;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(marcaController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void CrearMarcaExitoso() throws Exception {
        //Given
        NewMarcaRequest request = new NewMarcaRequest("Honda", "buena");
        mockMvc.perform(post("/ecommerce/admin/marcas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void CrearMarcaConNombreInvalido() throws Exception {
        //Given
        NewMarcaRequest request = new NewMarcaRequest(null, "buena");
        mockMvc.perform(post("/ecommerce/admin/marcas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void CrearMarcaConDescripcionInvalida() throws Exception {
        //Given
        NewMarcaRequest request = new NewMarcaRequest("Honda", null);
        mockMvc.perform(post("/ecommerce/admin/marcas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void CrearMarcaNombreDescripcionInvalido() throws Exception {
        //Given
        NewMarcaRequest request = new NewMarcaRequest(null, null);
        mockMvc.perform(post("/ecommerce/admin/marcas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
