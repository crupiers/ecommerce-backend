package programacion.eCommerceApp.integration.custom;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.repository.IProductoRepository;

@ActiveProfiles("test")
@Sql({"/scripts/base/reset_db.sql", "/scripts/base/test_data.sql"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = eCommerceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class eliminarProductoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IProductoRepository productoRepository;

    private Integer productoId;

    @BeforeEach
    public void setUp() throws Exception {
        // Clear the database
        productoRepository.deleteAll();

        // Create a new product using NewProductoRequest
        NewProductoRequest productoRequest = new NewProductoRequest(
            "Producto de prueba", // nombre
            "Descripción de prueba", // descripción
            10, // stock
            123456, // código barra
            100.0, // precio
            5, // umbral
            1, // colorId
            1, // tamanioId
            1, // categoriaId
            1  // marcaId
        );

        // Save the product via the controller endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/admin/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(productoRequest)))
            .andExpect(MockMvcResultMatchers.status().isCreated()); // Cambiar a isCreated() que es 201

        // Retrieve the saved product ID
        Producto producto = productoRepository.findAll().get(0);
        this.productoId = producto.getId(); 
    }

    @Test
    public void eliminarProducto_Exitoso() throws Exception {
        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/ecommerce/admin/productos/" + productoId))
            .andExpect(MockMvcResultMatchers.status().isOk());      

        // Then
        Producto productoActualizado = productoRepository.findById(productoId).get();
        Assertions.assertThat(productoActualizado.getEstado()).isEqualTo(Producto.ELIMINADO);
    }

    @Test
    public void eliminarProducto_NoExistente() throws Exception {
        // Given
        Integer productoId = 999;

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/ecommerce/admin/productos/" + productoId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("NO SE ENCONTRÓ EL PRODUCTO CON ID '" + productoId + "'"));

        // Then
        Assertions.assertThat(productoRepository.findById(productoId)).isEmpty();
    }

    @Test
    public void eliminarProducto_YaEliminado() throws Exception {
        // Given
        Producto producto = productoRepository.findById(productoId).get();
        producto.eliminar();
        productoRepository.save(producto);
        Assertions.assertThat(producto.getEstado()).isEqualTo(Producto.ELIMINADO);

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/ecommerce/admin/productos/" + productoId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("NO SE ENCONTRÓ EL PRODUCTO CON ID '" + productoId + "'"));

        // Then
        Assertions.assertThat(productoRepository.findById(productoId).get().getEstado()).isEqualTo(Producto.ELIMINADO);
    }

    private String toJson(Object object) throws Exception {
        return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(object);
    }
}