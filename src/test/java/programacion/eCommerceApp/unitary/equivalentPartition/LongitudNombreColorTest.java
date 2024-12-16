package programacion.eCommerceApp.unitary.equivalentPartition;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import programacion.eCommerceApp.controller.ColorController;
import programacion.eCommerceApp.controller.request.NewColorRequest;
import programacion.eCommerceApp.service.IColorService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

public class LongitudNombreColorTest {
    @Mock
    
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @InjectMocks
    private ColorController ColorController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Inicializa el controlador y los mocks
        mockMvc = MockMvcBuilders.standaloneSetup(ColorController).build();
        objectMapper = new ObjectMapper(); // Instancia real del ObjectMapper
    }

    @Test
    public void testCrearColorNombreCorto() throws Exception {
        NewColorRequest request = new NewColorRequest("A", "Electrodomestico");
        mockMvc.perform(post("/ecommerce/colores") // Corrige la URL
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))) // Uso de instancia real
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testCrearColorNombreValidoMinimo() throws Exception {
        NewColorRequest request = new NewColorRequest("ro", "Electrodomestico");

        mockMvc.perform(post("/ecommerce/colores") // Corrige la URL
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCrearColorNombreValidoEnRango() throws Exception {
        NewColorRequest request = new NewColorRequest("Mediano", "Electrodomestico");

        mockMvc.perform(post("/ecommerce/colores") // Corrige la URL
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void testCrearColorDescripcionLarga() throws Exception {
        NewColorRequest request = new NewColorRequest("NombreTamañoConMasDe32Caracteres", "a");
        mockMvc.perform(post("/ecommerce/colores") // Corrige la URL
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
}
}