package programacion.eCommerceApp.unitary.decisionTables;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import programacion.eCommerceApp.controller.PedidoController;
import programacion.eCommerceApp.controller.request.NewPedidoRequest;
import programacion.eCommerceApp.service.PedidoService;

public class CrearPedidoTest {
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private PedidoService pedidoService;
    @InjectMocks
    private PedidoController pedidoController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();
        objectMapper = new ObjectMapper();
    }
    

    @Test
    public void testCrearPedidoExitoso() throws Exception {
        // Given
        List<Integer> detalles = List.of(1, 2, 3);
        NewPedidoRequest request = new NewPedidoRequest((Integer)1, detalles);
        mockMvc.perform(post("/ecommerce/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
            }
    @Test
    public void testCrearPedido_DetalleNulo() throws Exception {
        // Given
        NewPedidoRequest request = new NewPedidoRequest((Integer)1, null);

        // When
        mockMvc.perform(post("/ecommerce/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testCrearPedido_UsuarioNulo() throws Exception {
        // Given
        List<Integer> detalles = List.of(1, 2, 3);
        NewPedidoRequest request = new NewPedidoRequest(null, detalles);
        // When
        mockMvc.perform(post("/ecommerce/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testCrearPedido_Usuario_DetalleNulos() throws Exception {
        // Given
        NewPedidoRequest request = new NewPedidoRequest(null, null);
        mockMvc.perform(post("/ecommerce/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
