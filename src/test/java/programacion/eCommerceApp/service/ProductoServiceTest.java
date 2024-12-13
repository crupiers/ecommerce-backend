
package programacion.eCommerceApp.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.repository.IProductoRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock
    private IProductoRepository modelRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void eliminarProductoExistente() {
        // Arrange
        Integer id = 1;
        Producto producto = new Producto();
        producto.setId(id);
        when(modelRepository.findById(id)).thenReturn(Optional.of(producto));

        // Act
        ResponseEntity<Void> response = productoService.eliminar(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Producto.ELIMINADO, producto.getEstado());
        verify(modelRepository, times(1)).save(producto);
    }

    @Test
    void eliminarProductoNoExistente() {
        // Arrange
        Integer id = 1;
        when(modelRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> productoService.eliminar(id));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("NO SE ENCONTRÓ EL PRODUCTO CON ID: " + id, exception.getReason());
    }
}