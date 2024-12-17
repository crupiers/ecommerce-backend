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
import programacion.eCommerceApp.controller.request.NewTamanioRequest;
import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.service.ProductoService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.http.MediaType;

public class ValidarNombreProductoTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    @Mock
    private ProductoService productoService;
    @InjectMocks
    private ProductoController productoController;


    // private NewProductoRequest productoRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
        objectMapper = new ObjectMapper(); // Instancia real del ObjectMapper
    
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
        
        mockMvc.perform(post("/ecommerce/productos")
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
                productoRequest.codigoBarra()
        );

        when(productoService.crear(productoRequest)).thenReturn(productoResponse);

        mockMvc.perform(post("/ecommerce/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testCrearTamanioNombreValidoEnRango() throws Exception {
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
                productoRequest.codigoBarra()
        );

        when(productoService.crear(productoRequest)).thenReturn(productoResponse);
        mockMvc.perform(post("/ecommerce/productos")
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
        
        mockMvc.perform(post("/ecommerce/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoRequest))) // Uso de instancia real
                .andExpect(status().isBadRequest());
    }
}