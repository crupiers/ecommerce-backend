package programacion.eCommerceApp.unitary.limitValues;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.model.Categoria;
import programacion.eCommerceApp.model.Color;
import programacion.eCommerceApp.model.Marca;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.model.Tamanio;
import programacion.eCommerceApp.repository.IProductoRepository;
import programacion.eCommerceApp.service.ProductoService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarIdProductoTest {

@Mock
private IProductoRepository productoRepository;

@InjectMocks
private ProductoService productoService;

private Integer idPrueba;
private Producto productoMock;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
    idPrueba = 2;
    productoMock = Producto.builder()
        .id(idPrueba)
        .nombre("Producto de prueba")
        .descripcion("Descripción de prueba")
        .stock(10)
        .codigoBarra(123456)
        .precio(100.0)
        .estado(Producto.COMUN)
        .color(new Color())
        .tamanio(new Tamanio())
        .categoria(new Categoria())
        .marca(new Marca())
        .build();
}

@Test
public void testBuscarPorId() {

    
    // given BDDMockito
    given(productoRepository.findById(idPrueba)).willReturn(Optional.of(productoMock));
    ProductoResponse productoResponse = new ProductoResponse(idPrueba, "Producto", "No", 10.0, 1, idPrueba, "Categoria", "h", "a", "a", 121321);
    
    // when
    when(productoService.buscarPorId(idPrueba)).thenReturn(ResponseEntity.ok(productoResponse));
    ResponseEntity<ProductoResponse> responseEntity = productoService.buscarPorId(idPrueba);
    ProductoResponse productoBuscado = responseEntity.getBody();
    // then
    assertNotNull(productoBuscado);
    assertEquals(productoMock.getNombre(), productoBuscado.nombre());
    assertEquals(productoMock.getPrecio(), productoBuscado.precio());

    verify(productoRepository, times(1)).findById(idPrueba);
}

@Test
public void testBuscarPorIdNoEncontrado() {
    // given BDDMockito
    int idPrueba = 2;
    given(productoRepository.findById(idPrueba)).willReturn(Optional.empty());

    // when & then
    assertThrows(ResponseStatusException.class, () -> {
        productoService.buscarPorId(idPrueba);
    });
    verify(productoRepository, times(1)).findById(idPrueba);
}

}