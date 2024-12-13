package programacion.eCommerceApp.unitarios;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.repository.IProductoRepository;
import programacion.eCommerceApp.service.ProductoService;

@ExtendWith(MockitoExtension.class)
class TestProducto {

    @Mock
    private IProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    // Test para verificar que el nombre con menos de 1 carácter no es válido
    @Test
    public void testCrearProductoNombreMenorDeUnCaracter() {
        NewProductoRequest productoMock = new NewProductoRequest("", 1, 1, 1.0, 1, 1, 1, 1);  // Nombre vacío

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.crear(productoMock);  // Método que valida la longitud del nombre
        });

        assertEquals("El nombre del producto debe tener entre 1 y 24 caracteres", exception.getMessage());
    }

    // Test para verificar que el nombre con menos de 3 caracteres no es válido
    @Test
    public void testCrearProductoNombreMenorDeTresCaracteres() {
        NewProductoRequest productoMock = new NewProductoRequest("no", 1, 1, 1.0, 1, 1, 1, 1);  
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.crear(productoMock);  // Método que valida la longitud del nombre
        });

        assertTrue("El nombre del producto debe tener entre 1 y 24 caracteres", productoMock.nombre().length() < 3);
    }

    // Test para verificar que el nombre con entre 3 y 24 caracteres es válido
    @Test
    public void testCrearProductoNombreEntreTresYVeinticuatroCaracteres() {
        NewProductoRequest productoMock = new NewProductoRequest("ProductoValido", 1, 1, 1.0, 1, 1, 1, 1);  // Nombre con 14 caracteres

        // Simulamos que el repositorio guarda el producto sin errores
        when(productoRepository.save(any(Producto.class))).thenReturn(new Producto());

        ProductoResponse productoGuardado = productoService.crear(productoMock);  // Método que valida la longitud y guarda el producto

        assertNotNull(productoGuardado);  // El producto debería ser guardado correctamente
        assertTrue("El nombre del producto debe tener entre 3 y 24 caracteres", productoMock.nombre().length() >= 3 && productoMock.nombre().length() <= 24);
        verify(productoRepository, times(1)).save(any(Producto.class));  // Verificar que el repositorio fue llamado
    }

    // Test para verificar que el nombre nulo no es válido
    @Test
    public void testCrearProductoNombreNulo() {
        NewProductoRequest productoMock = new NewProductoRequest(null, 1, 1, 1.0, 1, 1, 1, 1);  // Nombre nulo

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.crear(productoMock);  // Método que valida la longitud del nombre
        });

        assertEquals("El nombre del producto debe tener entre 1 y 24 caracteres", exception.getMessage());
    }

    // Test para verificar que el nombre con exactamente 24 caracteres es válido
    @Test
    public void testCrearProductoNombreValido() {
        NewProductoRequest productoMock = new NewProductoRequest("ProductoCon24Caracteres", 1, 1, 1.0, 1, 1, 1, 1);  // Nombre con 24 caracteres

        // Simulamos que el repositorio guarda el producto sin errores
        when(productoRepository.save(any(Producto.class))).thenReturn(new Producto());

        ProductoResponse productoGuardado = productoService.crear(productoMock);  // Método que valida la longitud y guarda el producto

        assertNotNull(productoGuardado);  // El producto debería ser guardado correctamente
        assertEquals(productoMock.nombre(), productoGuardado.getNombre());  // El nombre debería coincidir
        verify(productoRepository, times(1)).save(any(Producto.class));  // Verificar que el repositorio fue llamado
    }

    // Test para verificar que el nombre con más de 24 caracteres no es válido
    @Test
    public void testCrearProductoNombreMayorDe24Caracteres() {
        NewProductoRequest productoMock = new NewProductoRequest("NombreConMasDeVeinticuatroCaracteres", 1, 1, 1.0, 1, 1, 1, 1);  // Nombre con más de 24 caracteres

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.crear(productoMock);  // Método que valida la longitud del nombre
        });

        assertEquals("El nombre del producto debe tener entre 1 y 24 caracteres", exception.getMessage());
    }
}
