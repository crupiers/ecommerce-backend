package programacion.eCommerceApp.integration.stateTransition;

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
import programacion.eCommerceApp.integration.Base.BaseIntegrationTest;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.repository.IProductoRepository;

@ActiveProfiles("test")
@Sql("/scripts/base/reset_db.sql")
@Sql("/scripts/productos/crear_contexto_producto.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = eCommerceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class CambiarEstadoProductoTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IProductoRepository productoRepository;

    @Sql(statements = {
            "INSERT INTO producto (id, nombre, descripcion, stock, codigo_barra, precio, umbral, estado, id_color, id_tamanio, id_categoria, id_marca) " +
                    "VALUES (1, 'Samsung Galaxy 11', 'Smartphone de alta gama', 10, 123456789, 799.99, 10, 0, 1, 1, 1, 1)"
    })
    @Test
    public void cambiarEstado_DisponibleAEliminado() throws Exception {
        // Given
        Integer productoId = 90;

                Producto producto = Producto.builder()
                .id(productoId)
                .nombre("Producto 1")
                .descripcion("Descripción 1")
                .stock(10)
                .codigoBarra(123456789)
                .precio(50.00)
                .umbral(5)
                .estado(0)
                .build();
        productoRepository.save(producto);
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
