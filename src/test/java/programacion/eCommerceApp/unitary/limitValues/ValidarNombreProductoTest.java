package programacion.eCommerceApp.unitary.limitValues;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import programacion.eCommerceApp.controller.ProductoController;
import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.controller.response.MovimientoStockResponse;
import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.service.ProductoService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;

public class ValidarNombreProductoTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    @Mock
    private ProductoService productoService;
    @InjectMocks
    private ProductoController productoController;

    private List<MovimientoStockResponse> movimientos;


    // private NewProductoRequest productoRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
        objectMapper = new ObjectMapper(); // Instancia real del ObjectMapper
        // Crear la lista de MovimientoStockResponse
        List<MovimientoStockResponse> movimientos = new ArrayList<>();
        movimientos.add(new MovimientoStockResponse(
                1,                   // id
                50,                  // cantidad
                "Stock inicial",     // motivo
                "Entrada",           // tipoMovimiento
                "admin",             // createdBy
                "2024-12-17",        // fechaMovimiento
                "10:00 AM"           // horaMovimiento
        ));
    
}

    @Test
    public void testCrearProductoNombreMenorLimiteInferior() throws Exception {
        String nombre = "a";

        NewProductoRequest productoRequest = new NewProductoRequest(
                nombre,
                "Descripción de prueba",
                    10,
                    123456,
                    100.0,
                    1,
                    1, // colorId
                    1, // tamanioId
                    1, // categoriaId
                    1  // marcaId
            
                );
        
        mockMvc.perform(post("/ecommerce/admin/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoRequest))) // Uso de instancia real
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testCrearProductoNombreLimiteInferior() throws Exception {
        String nombre = "ab";
        
        
        NewProductoRequest productoRequest = new NewProductoRequest(
                nombre,
                "Descripción de prueba",
                    10,
                    123456,
                    100.0,
                    1,
                    1, // colorId
                    1, // tamanioId
                    1, // categoriaId
                    1  // marcaId
            
                );
        ProductoResponse productoResponse = new ProductoResponse(
                1,
                nombre,
                productoRequest.descripcion(),
                productoRequest.precio(),
                productoRequest.umbral(),
                productoRequest.stock(),
                "Categoria",
                "Marca",
                "Tamanio",
                "Color",
                productoRequest.codigoBarra(),
                movimientos,
                nombre, nombre, nombre, nombre, nombre, 1
        );

        when(productoService.crear(productoRequest)).thenReturn(productoResponse);

        mockMvc.perform(post("/ecommerce/admin/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testCrearTamanioNombreValidoEnRango() throws Exception {
        String nombre = "Heladera";

        NewProductoRequest productoRequest = new NewProductoRequest(
                nombre,
                "Descripción de prueba",
                    10,
                    123456,
                    100.0,
                    1,
                    1, // colorId
                    1, // tamanioId
                    1, // categoriaId
                    1  // marcaId
            
                );
        ProductoResponse productoResponse = new ProductoResponse(
                1,
                nombre,
                productoRequest.descripcion(),
                productoRequest.precio(),
                productoRequest.umbral(),
                productoRequest.stock(),
                "Categoria",
                "Marca",
                "Tamanio",
                "Color",
                productoRequest.codigoBarra(), movimientos,
                nombre, nombre, nombre, nombre, nombre, null
        );

        when(productoService.crear(productoRequest)).thenReturn(productoResponse);
        mockMvc.perform(post("/ecommerce/admin/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoRequest)))
                .andExpect(status().isOk());
    }
    
    @Test
    public void testCrearProductoNombreMayorLimiteSuperior() throws Exception {
        String nombre = "Producto de ejemplo con la longitud igual a 65 caracteres12345678";

        NewProductoRequest productoRequest = new NewProductoRequest(
                nombre,
                "Descripción de prueba",
                    10,
                    123456,
                    100.0,
                    1,
                    1, // colorId
                    1, // tamanioId
                    1, // categoriaId
                    1  // marcaId
            
                );
        
        mockMvc.perform(post("/ecommerce/admin/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoRequest))) // Uso de instancia real
                .andExpect(status().isBadRequest());
    }
}