package programacion.eCommerceApp.unitary.decisionTables;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import programacion.eCommerceApp.controller.request.NewDetallePedidoRequest;
import programacion.eCommerceApp.controller.response.DetallePedidoResponse;
import programacion.eCommerceApp.model.Categoria;
import programacion.eCommerceApp.model.Color;
import programacion.eCommerceApp.model.DetallePedido;
import programacion.eCommerceApp.model.Marca;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.model.Tamanio;
import programacion.eCommerceApp.repository.IDetallePedidoRepository;
import programacion.eCommerceApp.repository.IProductoRepository;
import programacion.eCommerceApp.service.DetallePedidoService;
import programacion.eCommerceApp.service.EmailService;
    /*
    * 
    Scenario1: Cantidad solicitada es menor o igual al stock disponible
    Given que el stock disponible del producto es 6
    When intento comprar 4 unidades
    Then la operación se permite con el mensaje “Pedido aceptado"

    Scenario2: Cantidad solicitada es igual al stock disponible
    Given que el stock disponible del producto es 2
    When intento comprar 2 unidades
    Then la operación se permite con el mensaje “Pedido aceptado”

    Scenario3: Cantidad solicitada excede el stock disponible
    Given que el stock disponible del producto es 2
    When intento comprar 3 unidades
    Then la operación se rechaza con el mensaje "Error: stock insuficiente"

    Scenario4: Stock del producto es 0
    Given que el stock disponible del producto es 0
    When intento comprar 1 unidad
    Then la operación se rechaza con el mensaje "Error: stock insuficiente"

 */
public class ValidarStockProductoEnPedidoTest {
    @Mock
    private IProductoRepository productoRepository;
    @Mock
    private IDetallePedidoRepository detallePedidoRepository;
    @Mock
    private EmailService emailService;
    @Mock
    Producto producto;
    @InjectMocks 
    private DetallePedidoService detallePedidoService;    
    
    DetallePedido detallePedido;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        Color color = new Color(1, "Rojo", "descripcionColor", 1, null, null, null, null, null);
        Tamanio tamanio = new Tamanio(1, "Grande", "descripcionTamanio", 0, null, null, null, null, null);
        Categoria categoria = new Categoria(1, "Categoria", "descripcionCategoria", 0, null, null, null, null, null);
        Marca marca = new Marca(1, "Marca", "descripcionMarca", 0, null, null, null, null, null);

        producto = Producto.builder()
        .id(1)
        .nombre("Producto de prueba")
        .descripcion("Descripción de prueba")
        .stock(10)
        .codigoBarra(123456)
        .precio(100.0)
        .umbral(5)
        .estado(Producto.COMUN)
        .color(color)
        .tamanio(tamanio)
        .categoria(categoria)
        .marca(marca)
        .build();
        
        // detallePedido = DetallePedido.builder()
        // .id(1)
        // .cantidad(4)
        // .producto(producto)
        // .subtotal(0)
        // .build();
    
}

@Test
public void cantidadMenorIgualStockDisponible() {
    // Given
    NewDetallePedidoRequest detallePedidoRequest = new NewDetallePedidoRequest(1, 4);
    producto.setStock(6);
    when(productoRepository.findById(detallePedidoRequest.idProducto())).thenReturn(Optional.of(producto));
    when(detallePedidoRepository.save(any(DetallePedido.class))).thenAnswer(i -> i.getArguments()[0]);

    // When
    DetallePedidoResponse response = detallePedidoService.crear(detallePedidoRequest);

    // Then
    assertEquals(detallePedidoRequest.cantidad(), response.cantidad());
}

@Test
public void cantidadIgualStockDisponible() {
    // Given
    NewDetallePedidoRequest detallePedidoRequest = new NewDetallePedidoRequest(1, 2);
    producto.setStock(2);
    when(productoRepository.findById(detallePedidoRequest.idProducto())).thenReturn(Optional.of(producto));
    when(detallePedidoRepository.save(any(DetallePedido.class))).thenAnswer(i -> i.getArguments()[0]);

    // When
    DetallePedidoResponse response = detallePedidoService.crear(detallePedidoRequest);

    // Then
    assertEquals(detallePedidoRequest.cantidad(), response.cantidad());
}

@Test
public void cantidadExcedeStockDisponible() {
    // Given
    NewDetallePedidoRequest detallePedidoRequest = new NewDetallePedidoRequest(1, 3);
    producto.setStock(2);
    when(productoRepository.findById(detallePedidoRequest.idProducto())).thenReturn(Optional.of(producto));

    // When & Then
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        detallePedidoService.crear(detallePedidoRequest);
    });
    assertEquals("NO HAY STOCK SUFICIENTE PARA EL PRODUCTO CON ID: 1", exception.getMessage());
}

@Test
public void stockProductoEsCero() {
    // Given
    NewDetallePedidoRequest detallePedidoRequest = new NewDetallePedidoRequest(1, 1);
    producto.setStock(0);
    when(productoRepository.findById(detallePedidoRequest.idProducto())).thenReturn(Optional.of(producto));

    // When & Then
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        detallePedidoService.crear(detallePedidoRequest);
    });
    assertEquals("NO HAY STOCK SUFICIENTE PARA EL PRODUCTO CON ID: 1", exception.getMessage());
}
}
