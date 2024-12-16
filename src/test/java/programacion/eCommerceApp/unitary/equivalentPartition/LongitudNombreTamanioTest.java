package programacion.eCommerceApp.unitary.equivalentPartition;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import programacion.eCommerceApp.controller.TamanioController;
import programacion.eCommerceApp.controller.request.NewTamanioRequest;
import programacion.eCommerceApp.service.ITamanioService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

public class LongitudNombreTamanioTest {
    @Mock
    private ITamanioService tamanioService;
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @InjectMocks
    private TamanioController tamanioController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Inicializa el controlador y los mocks
        mockMvc = MockMvcBuilders.standaloneSetup(tamanioController).build();
        objectMapper = new ObjectMapper(); // Instancia real del ObjectMapper
    }

    @Test
    public void testCrearTamanioNombreCorto() throws Exception {
        NewTamanioRequest request = new NewTamanioRequest("A", "Electrodomestico");
        mockMvc.perform(post("/ecommerce/tamanios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))) // Uso de instancia real
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testCrearTamanioNombreValidoMinimo() throws Exception {
        NewTamanioRequest request = new NewTamanioRequest("ro", "Electrodomestico");

        mockMvc.perform(post("/ecommerce/tamanios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCrearTamanioNombreValidoEnRango() throws Exception {
        NewTamanioRequest request = new NewTamanioRequest("Mediano", "Electrodomestico");

        mockMvc.perform(post("/ecommerce/tamanios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void testCrearTamanioDescripcionLarga() throws Exception {
        NewTamanioRequest request = new NewTamanioRequest("NombreTamañoConMasDe32Caracteres", "a");
        mockMvc.perform(post("/ecommerce/tamanios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
}
}