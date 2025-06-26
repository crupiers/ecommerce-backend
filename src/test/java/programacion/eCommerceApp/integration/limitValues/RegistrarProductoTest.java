package programacion.eCommerceApp.integration.limitValues;

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
import programacion.eCommerceApp.integration.Base.BaseIntegrationTest;
import programacion.eCommerceApp.repository.IProductoRepository;
@ActiveProfiles("test")
@Sql("/scripts/base/reset_db.sql")
@Sql("/scripts/productos/crear_contexto_producto.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = eCommerceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class RegistrarProductoTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IProductoRepository productoRepository;
    NewProductoRequest productoRequest;

    @BeforeEach
    public void setUp() {
        
    }

    @Test
    public void registrarProducto_PrecioValido() throws Exception {
        // Given el controlador de producto está configurado para recibir peticiones de registro
        Double precioProducto = 50.00;
        productoRequest = new NewProductoRequest(
                "Cocina", // nombre
                "4 hornayas", // descripción
                10, // stock
                123456789, // código barra
                precioProducto, // precio
                5, // umbral
                1, // colorId
                1, // tamanioId
                1, // categoriaId
                1  // marcaId
        );
    
        // When el administrador registra un producto con precio "50" y todos los demás atributos correctos

        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/admin/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(productoRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        //Then el producto se registra correctamente en el sistema And el precio del producto es "50"
        mockMvc.perform(MockMvcRequestBuilders.get("/ecommerce/productos/{id}", 1))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.precio").value(productoRequest.precio()));
        //And el producto aparece en el repositorio con el precio "50.00"
        Assertions.assertThat(productoRepository.findById(1).get().getPrecio()).isEqualTo(precioProducto);
    }

    @Test
    public void registrarProducto_PrecioCero() throws Exception {
        // Given el controlador de producto está configurado para recibir peticiones de registro
        Double precioProducto = 0.00;
        productoRequest = new NewProductoRequest(
                "Cocina", // nombre
                "4 hornayas", // descripción
                10, // stock
                123456789, // código barra
                precioProducto, // precio
                5, // umbral
                1, // colorId
                1, // tamanioId
                1, // categoriaId
                1  // marcaId
        );

        // When el usuario intenta registrar un producto con precio "0" y todos los demás atributos correctos
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/admin/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(productoRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("EL PRECIO DEBE SER MAYOR A 0"));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/ecommerce/productos/{id}", 1))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void registrarProducto_PrecioNegativo() throws Exception {

        // Given el controlador de producto está configurado para recibir peticiones de registro

        Double precioProducto = -10.00;
        productoRequest = new NewProductoRequest(
                "Cocina", // nombre
                "4 hornayas", // descripción
                10, // stock
                123456789, // código barra
                precioProducto, // precio
                5, // umbral
                1, // colorId
                1, // tamanioId
                1, // categoriaId
                1  // marcaId
        );
        // When el usuario intenta registrar un producto con precio "-10" y todos los demás atributos correctos

        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/admin/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(productoRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("EL PRECIO DEBE SER MAYOR A 0"));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/ecommerce/productos/{id}", 1))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
