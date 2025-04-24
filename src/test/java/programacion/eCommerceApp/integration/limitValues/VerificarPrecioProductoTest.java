package programacion.eCommerceApp.integration.limitValues;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import programacion.eCommerceApp.controller.request.NewDetallePedidoRequest;
import programacion.eCommerceApp.controller.request.NewPedidoRequest;
import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.eCommerceApplication;
import programacion.eCommerceApp.integration.Base.BaseIntegrationTest;

@Sql("/scripts/base/reset_db.sql")
@Sql("/scripts/pedidos/crear_contexto_pedido.sql") //INSERTO CONTEXTO PARA CREAR PRODUCTO
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = eCommerceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class VerificarPrecioProductoTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    NewProductoRequest productoRequest;

    private final String URIPost = "/ecommerce/admin/productos";
    private final String URIGet = "/ecommerce/productos/{id}";

    private String nombre;
    private String descripcion;
    private int stock;
    private int codigoBarra;
    private double precio;
    private int umbral;
    private int colorId;
    private int tamanioId;
    private int categoriaId;
    private int marcaId;

    @BeforeEach
    public void setUp() {
        nombre = "NOMBRE DE EJEMPLO";
        descripcion = "DESCRIPCIÓN DE EJEMPLO";
        stock = 100;
        codigoBarra = 288499929;
        umbral = 10;
        colorId = 1; //INSERTO LOS VALORES QUE ME DA EL CONTEXTO DEL ARCHIVO SQL DE ARRIBA
        tamanioId = 1;
        categoriaId = 1;
        marcaId = 1;
    }

    @Test
    public void givenProducto_whenPrecio50_thenProductoRegistrado() throws Exception {
        precio = 50.0;

        productoRequest = new NewProductoRequest(
                nombre,
                descripcion,
                stock,
                codigoBarra,
                precio,
                umbral,
                colorId,
                tamanioId,
                categoriaId,
                marcaId
        );

        mockMvc.perform(MockMvcRequestBuilders.post(URIPost)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(productoRequest))
        ).andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get(URIGet, 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.precio").value(50));

    }

    @Test
    public void givenProducto_whenPrecio0_thenProductoNoRegistrado() throws Exception {
        precio = 0;

        productoRequest = new NewProductoRequest(
                nombre,
                descripcion,
                stock,
                codigoBarra,
                precio,
                umbral,
                colorId,
                tamanioId,
                categoriaId,
                marcaId
        );

        mockMvc.perform(MockMvcRequestBuilders.post(URIPost)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(productoRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.details").value("EL PRECIO DEBE SER MAYOR A 0"));

        mockMvc.perform(MockMvcRequestBuilders.get(URIGet, 1))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void givenProducto_whenPrecioNegativo_thenProductoNoRegistrado() throws Exception {
        precio = -10;

        productoRequest = new NewProductoRequest(
                nombre,
                descripcion,
                stock,
                codigoBarra,
                precio,
                umbral,
                colorId,
                tamanioId,
                categoriaId,
                marcaId
        );

        mockMvc.perform(MockMvcRequestBuilders.post(URIPost)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(productoRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.details").value("EL PRECIO DEBE SER MAYOR A 0"));

        mockMvc.perform(MockMvcRequestBuilders.get(URIGet, 1))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
