package programacion.eCommerceApp.integration.decisionTables;

import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import programacion.eCommerceApp.eCommerceApplication;
import programacion.eCommerceApp.controller.request.NewDetallePedidoRequest;
import programacion.eCommerceApp.controller.request.NewPedidoRequest;
import programacion.eCommerceApp.integration.Base.BaseIntegrationTest;
import programacion.eCommerceApp.repository.IDetallePedidoRepository;
import programacion.eCommerceApp.repository.IPedidoRepository;

@Sql("/scripts/base/reset_db.sql")
@Sql("/scripts/pedidos/crear_contexto_pedido_all.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = eCommerceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class CrearPedidoTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IPedidoRepository pedidoRepository;
    @Autowired
    private IDetallePedidoRepository detallePedidoRepository;
    @Test
    public void registrarPedido_UsuarioYDetallesValidos() throws Exception {
        // Given
        NewPedidoRequest pedidoRequest = new NewPedidoRequest(1, List.of(1));
        NewDetallePedidoRequest detallePedidoRequest = new NewDetallePedidoRequest(1, 1);
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/detallesPedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(detallePedidoRequest)));
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(pedidoRequest)))
        // Then
            .andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertThat(pedidoRepository.findById(1)).isNotNull();
        Assertions.assertThat(detallePedidoRepository.findById(1)).isNotNull();
    }

    @Test
    public void registrarPedido_DetallesNulos() throws Exception {
        // Given
        NewPedidoRequest pedidoRequest = new NewPedidoRequest(1, null);
        NewDetallePedidoRequest detallePedidoRequest = new NewDetallePedidoRequest(1, 1);
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/detallesPedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(detallePedidoRequest)));
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(pedidoRequest)))
        // Then
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("Los detalles de pedido no pueden ser nulos"));
        Assertions.assertThat(pedidoRepository.findById(1)).isNull();
    }

    @Test
    public void registrarPedido_UsuarioNulo() throws Exception {
        NewPedidoRequest pedidoRequest = new NewPedidoRequest(null, List.of(1));
        NewDetallePedidoRequest detallePedidoRequest = new NewDetallePedidoRequest(1, 1);
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/detallesPedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(detallePedidoRequest)));
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(pedidoRequest)))
        // Then
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("El usuario no puede ser nulo"));
        Assertions.assertThat(pedidoRepository.findById(1)).isNull();
    }

    @Test
    public void registrarPedido_UsuarioYDetallesNulos() throws Exception {
        // Given
        NewPedidoRequest pedidoRequest = new NewPedidoRequest(null, List.of(1));
        NewDetallePedidoRequest detallePedidoRequest = new NewDetallePedidoRequest(1, 1);
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/detallesPedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(detallePedidoRequest)));
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(pedidoRequest)))
        // Then
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("El usuario no puede ser nulo"));        Assertions.assertThat(pedidoRepository.findById(1)).isNull();
    }
}
