package programacion.eCommerceApp.integration.limitValues;

import java.util.List;

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
import programacion.eCommerceApp.integration.Base.BaseIntegrationTest;

import programacion.eCommerceApp.eCommerceApplication;
import programacion.eCommerceApp.controller.request.NewDetallePedidoRequest;
import programacion.eCommerceApp.controller.request.NewPedidoRequest;
import programacion.eCommerceApp.controller.request.NewProductoRequest;

@Sql("/scripts/base/reset_db.sql")
@Sql("/scripts/pedidos/crear_contexto_pedido.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = eCommerceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class VerificarStockPedidoTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    NewProductoRequest productoRequest;
    NewDetallePedidoRequest detallePedidoRequest;
    NewPedidoRequest pedidoRequest;

    @BeforeEach
    public void setUp() {
        productoRequest = new NewProductoRequest(
                "Cocina", // nombre
                "4 hornayas", // descripción
                10, // stock
                123456789, // código barra
                99.99, // precio
                5, // umbral
                1, // colorId
                1, // tamanioId
                1, // categoriaId
                1  // marcaId
        );
        pedidoRequest = new NewPedidoRequest(
                1, // idUsuario
                List.of(1) // idDetallesPedido
        );
    }

    @Test
    public void pedidoCantidad_EnLímiteInferior_Stock() throws Exception {
        // Given
            Integer cantidadComprar = 1;
            detallePedidoRequest = new NewDetallePedidoRequest(
                1, // idProducto
                cantidadComprar // cantidad
            );

        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/admin/productos")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(toJson(productoRequest))) 
            .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/detallesPedidos")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(toJson(detallePedidoRequest))) 
            .andExpect(MockMvcResultMatchers.status().isOk());
        
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/pedidos")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(toJson(pedidoRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        // Verificar que el stock se actualizó
        mockMvc.perform(MockMvcRequestBuilders.get("/ecommerce/productos/{id}", 1)) 
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(productoRequest.stock()-detallePedidoRequest.cantidad()));
    }
    @Test
    public void pedidoCantidad_Igual_LímiteInferior_Stock() throws Exception {
        // Given
            Integer cantidadComprar = 10;

            detallePedidoRequest = new NewDetallePedidoRequest(
                1, // idProducto
                cantidadComprar // cantidad
            );
           
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/admin/productos")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(toJson(productoRequest))) 
            .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/detallesPedidos")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(toJson(detallePedidoRequest))) 
            .andExpect(MockMvcResultMatchers.status().isOk());
        
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/pedidos")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(toJson(pedidoRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        // Verificar que el stock se actualizó
        mockMvc.perform(MockMvcRequestBuilders.get("/ecommerce/productos/{id}", 1)) 
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(productoRequest.stock()-detallePedidoRequest.cantidad()));
    }
    @Test
    public void pedidoCantidad_Mayor_Stock() throws Exception {
        // Given
            Integer cantidadComprar = 11;
            detallePedidoRequest = new NewDetallePedidoRequest(
                1, // idProducto
                cantidadComprar // cantidad
            );

        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/admin/productos")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(toJson(productoRequest))) 
            .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/detallesPedidos")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(toJson(detallePedidoRequest))) 
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("NO HAY STOCK SUFICIENTE PARA EL PRODUCTO CON ID: 1"));
        
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/pedidos")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(toJson(pedidoRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("DETALLE PEDIDO NO ENCONTRADO CON ID: 1"));

        // Verificar que el stock NO se actualizó
        mockMvc.perform(MockMvcRequestBuilders.get("/ecommerce/productos/{id}", 1)) 
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(productoRequest.stock()));
    }
    @Test
    public void pedidoCantidad_Igual_Cero() throws Exception {
        // Given
            Integer cantidadComprar = 0;
            detallePedidoRequest = new NewDetallePedidoRequest(
                1, // idProducto
                cantidadComprar // cantidad
            );
            
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/admin/productos")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(toJson(productoRequest))) 
            .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/detallesPedidos")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(toJson(detallePedidoRequest))) 
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("LA CANTIDAD DEBE SER MAYOR A 0"));
        
        mockMvc.perform(MockMvcRequestBuilders.post("/ecommerce/pedidos")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(toJson(pedidoRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("DETALLE PEDIDO NO ENCONTRADO CON ID: 1"));

        // Verificar que el stock NO se actualizó
        mockMvc.perform(MockMvcRequestBuilders.get("/ecommerce/productos/{id}", 1)) 
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(productoRequest.stock()));
    }
}
