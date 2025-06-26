package programacion.eCommerceApp.unitary.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import programacion.eCommerceApp.controller.MovimientoStockController;
import programacion.eCommerceApp.controller.request.NewMovimientoStockRequest;
import programacion.eCommerceApp.model.MovimientoStock;
import programacion.eCommerceApp.service.MovimientoStockService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class ValidarMovimientoStockTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private MovimientoStockService movimientoStockService;

    @InjectMocks
    private MovimientoStockController movimientoStockController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movimientoStockController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testMovimientoValido() throws Exception {
        NewMovimientoStockRequest request = new NewMovimientoStockRequest(4, "Si", MovimientoStock.salida);
        mockMvc.perform(post("/ecommerce/admin/movimientoStock/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void testMovimientoCantidadNula() throws Exception {
        NewMovimientoStockRequest request = new NewMovimientoStockRequest(null, "Si", MovimientoStock.salida);
        mockMvc.perform(post("/ecommerce/admin/movimientoStock/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());    }

    @Test
    public void testMovimientoMotivoNulo() throws Exception {
        NewMovimientoStockRequest request = new NewMovimientoStockRequest(1, null, MovimientoStock.salida);

        mockMvc.perform(post("/ecommerce/admin/movimientoStock/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());    }

    @Test
    public void testMovimientoCantidadYMotivoNulos() throws Exception {
        NewMovimientoStockRequest request = new NewMovimientoStockRequest(null, null, MovimientoStock.salida);

        mockMvc.perform(post("/ecommerce/admin/movimientoStock/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testMovimientoIdProductoInvalido() throws Exception {
        NewMovimientoStockRequest request = new NewMovimientoStockRequest(null, "Si", "");

        mockMvc.perform(post("/ecommerce/admin/movimientoStock/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    
    }

    @Test
    public void testMovimientoIdProductoInvalidoYCantidadNula() throws Exception {
        NewMovimientoStockRequest request = new NewMovimientoStockRequest(null, "Si", null);

        mockMvc.perform(post("/ecommerce/admin/movimientoStock/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testMovimientoIdProductoInvalidoYMotivoNulo() throws Exception {
        NewMovimientoStockRequest request = new NewMovimientoStockRequest(null, null, MovimientoStock.salida);

        mockMvc.perform(post("/ecommerce/admin/movimientoStock/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testMovimientoIdProductoInvalidoMotivoYCantidadNulos() throws Exception {
        NewMovimientoStockRequest request = new NewMovimientoStockRequest(null, null, null);

        mockMvc.perform(post("/ecommerce/admin/movimientoStock/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
