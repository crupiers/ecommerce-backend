package programacion.eCommerceApp;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.service.ProductoService;

@SpringBootTest
class TestProducto {

    @Autowired
    private Producto producto;

    @MockBean
    private ProductoService productoService;
    
    @Test
    public void testCNombreLongitudProductoEmpty() {
        producto.setNombre("");
        String nombreProducto = producto.getNombre();
        assertTrue("La longitud del nombre del producto debe ser mayor a 0 y menor o igual a 24",nombreProducto.length() > 0 && nombreProducto.length() <= 24 );
    }
    
    @Test
    public void testCNombreLongitudProductoMax() {
        producto.setNombre("NombreProductoCon24Carac");
        String nombreProducto = producto.getNombre();
        assertTrue("La longitud del nombre del producto debe ser mayor a 0 y menor o igual a 24",nombreProducto.length() > 0 && nombreProducto.length() <= 24 );
    }
    @Test
    public void testCNombreLongitudProductoValid() {
        producto.setNombre("“NombreProducto”");
        String nombreProducto = producto.getNombre();
        assertTrue("La longitud del nombre del producto debe ser mayor a 0 y menor o igual a 24",nombreProducto.length() > 0 && nombreProducto.length() <= 24 );
    }
    @Test
    public void testCNombreLongitudProductoOverlay() {
        producto.setNombre("“NombreProductoCon24MasDeCaracteres”");
        String nombreProducto = producto.getNombre();
        assertTrue("La longitud del nombre del producto debe ser mayor a 0 y menor o igual a 24",nombreProducto.length() > 0 && nombreProducto.length() <= 24 );
    }
}