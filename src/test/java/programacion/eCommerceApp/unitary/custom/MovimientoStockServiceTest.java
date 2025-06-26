package programacion.eCommerceApp.unitary.custom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import programacion.eCommerceApp.controller.request.NewMovimientoStockRequest;
import programacion.eCommerceApp.controller.response.MovimientoStockResponse;
import programacion.eCommerceApp.model.MovimientoStock;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.repository.IMovimientoStockRepository;
import programacion.eCommerceApp.repository.IProductoRepository;
import programacion.eCommerceApp.service.EmailService;
import programacion.eCommerceApp.service.MovimientoStockService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
@ActiveProfiles("test")
class MovimientoStockServiceTest {

    @Mock
    private IMovimientoStockRepository movimientoStockRepository;

    @Mock
    private IProductoRepository productoRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private MovimientoStockService movimientoStockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearEntradaStockValida() {
        Producto producto = new Producto();
        producto.setStock(2);
        producto.setUmbral(1);
        when(productoRepository.findById(any())).thenReturn(Optional.of(producto));
        Integer stockAntiguo = producto.getStock();
        NewMovimientoStockRequest request = new NewMovimientoStockRequest(1, "Si", MovimientoStock.entrada);
        MovimientoStockResponse response = movimientoStockService.crear(1, request);
        System.out.println("response: " + response);
        

        assertEquals(response.cantidad() + stockAntiguo, producto.getStock());

        verify(movimientoStockRepository, times(1)).save(any(MovimientoStock.class));
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void testCrearEntradaStockCantidadNegativa() {
        Producto producto = new Producto();
        producto.setStock(2);
        when(productoRepository.findById(any())).thenReturn(Optional.of(producto));

        NewMovimientoStockRequest request = new NewMovimientoStockRequest(-1, "Si", MovimientoStock.entrada);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            movimientoStockService.crear(1, request);
        });

        assertEquals("La cantidad de entrada tiene que ser positiva.", exception.getMessage());
        assertEquals(2, producto.getStock());
        verify(movimientoStockRepository, never()).save(any(MovimientoStock.class));
        verify(productoRepository, never()).save(producto);
    }

    @Test
    void testCrearEntradaStockValidaIgualStock() {
        Producto producto = new Producto();
        producto.setStock(2);
        producto.setUmbral(1);
        Integer stockAntiguo = producto.getStock();
        when(productoRepository.findById(any())).thenReturn(Optional.of(producto));

        NewMovimientoStockRequest request = new NewMovimientoStockRequest(2, "Si", MovimientoStock.entrada);
        MovimientoStockResponse response = movimientoStockService.crear(1, request);

        assertEquals(stockAntiguo + response.cantidad(), producto.getStock());
        verify(movimientoStockRepository, times(1)).save(any(MovimientoStock.class));
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void testCrearSalidaStockCantidadMayorQueActual() {
        Producto producto = new Producto();
        producto.setStock(2);
        when(productoRepository.findById(any())).thenReturn(Optional.of(producto));

        NewMovimientoStockRequest request = new NewMovimientoStockRequest(4, "Si", MovimientoStock.salida);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            movimientoStockService.crear(1, request);
        });

        assertEquals("El stock del producto es insuficiente para realizar la salida.", exception.getMessage());
        assertEquals(2, producto.getStock());
        verify(movimientoStockRepository, never()).save(any(MovimientoStock.class));
        verify(productoRepository, never()).save(producto);
    }

    @Test
    void testCrearSalidaStockCantidadNegativa() {
        Producto producto = new Producto();
        producto.setStock(3);
        producto.setUmbral(1);
        when(productoRepository.findById(any())).thenReturn(Optional.of(producto));

        NewMovimientoStockRequest request = new NewMovimientoStockRequest(-1, "Si", MovimientoStock.salida);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            movimientoStockService.crear(1, request);
        });
        assertEquals("La cantidad de entrada tiene que ser positiva.", exception.getMessage());
        verify(movimientoStockRepository, never()).save(any(MovimientoStock.class));
        verify(productoRepository, never()).save(producto);
    }

    @Test
    void testCrearSalidaStockCantidadValida() {
        Producto producto = new Producto();
        producto.setStock(3);
        producto.setUmbral(1);
        Integer stockAntiguo = producto.getStock();
        when(productoRepository.findById(any())).thenReturn(Optional.of(producto));

        NewMovimientoStockRequest request = new NewMovimientoStockRequest(2, "Si", MovimientoStock.salida);
        MovimientoStockResponse response = movimientoStockService.crear(1, request);

        assertEquals(stockAntiguo - response.cantidad(), producto.getStock());
        verify(movimientoStockRepository, times(1)).save(any(MovimientoStock.class));
        verify(productoRepository, times(1)).save(producto);
    }    
}
