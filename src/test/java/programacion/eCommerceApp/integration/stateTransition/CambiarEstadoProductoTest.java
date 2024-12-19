package programacion.eCommerceApp.integration.stateTransition;

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
import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.integration.Base.BaseIntegrationTest;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.repository.IProductoRepository;

@Sql("/scripts/base/reset_db.sql")
@Sql("/scripts/productos/crear_contexto_pedido_all.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = eCommerceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class CambiarEstadoProductoTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IProductoRepository productoRepository;

    @Test
    public void cambiarEstado_DisponibleAEliminado() throws Exception {
        // Given
        Integer productoId = 1;
        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/ecommerce/productos/" + productoId)
                .contentType(MediaType.APPLICATION_JSON))
        // Then
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Producto eliminado exitosamente"));
        Assertions.assertThat(productoRepository.findById(productoId).get().getEstado()).isEqualTo(1);
    }

    @Test
    public void cambiarEstado_EliminadoADisponible() throws Exception {
        // Given
        Integer productoId = 12;
        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/ecommerce/productos/recuperar/" + productoId)
                .contentType(MediaType.APPLICATION_JSON))
        // Then
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Producto recuperado exitosamente"));
        Assertions.assertThat(productoRepository.findById(productoId).get().getEstado()).isEqualTo(0);
    }

    @Test
    public void recuperarProductoInexistente() throws Exception {
        // Given
        Integer productoId = 999;
        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/ecommerce/productos/recuperar/" + productoId)
                .contentType(MediaType.APPLICATION_JSON))
        // Then
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No se encontró el producto con ID: " + productoId));
    }

    @Test
    public void eliminarProductoInexistente() throws Exception {
        // Given
        Integer productoId = 999;
        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/ecommerce/productos/" + productoId)
                .contentType(MediaType.APPLICATION_JSON))
        // Then
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No se encontró el producto con ID: " + productoId));
    }

    @Test
    public void eliminarProductoYaEliminado() throws Exception {
        // Given
        Integer productoId = 123;
        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/ecommerce/productos/" + productoId)
                .contentType(MediaType.APPLICATION_JSON))
        // Then
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("El producto ya está eliminado"));
    }
}
