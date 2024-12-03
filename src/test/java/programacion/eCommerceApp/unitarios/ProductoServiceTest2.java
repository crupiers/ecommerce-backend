package programacion.eCommerceApp.unitarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.repository.IProductoRepository;
import programacion.eCommerceApp.service.ProductoService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest2 {

    @Mock
    private IProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId() {
        // Arrange
        Integer idPrueba = 1;
        Producto productoMock = new Producto();
        productoMock.setId(idPrueba);
        when(productoRepository.findById(idPrueba)).thenReturn(Optional.of(productoMock));

        // Act
        Producto productoBuscado = productoService.buscarPorId(idPrueba);

        // Assert
        assertNotNull(productoBuscado);
        assertEquals(idPrueba, productoBuscado.getId());

        verify(productoRepository, times(1)).findById(idPrueba);
    }

    @Test
    void testBuscarPorIdNoEncontrado() {
        // Arrange
        Integer idPrueba = 1;
        when(productoRepository.findById(idPrueba)).thenReturn(Optional.empty());

        // Act
        Producto productoBuscado = productoService.buscarPorId(idPrueba);

        // Assert
        assertNull(productoBuscado);
        verify(productoRepository, times(1)).findById(idPrueba);
    }
}