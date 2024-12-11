package programacion.eCommerceApp.unitary.stateTransition;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.mapper.ProductoMapper;
import programacion.eCommerceApp.model.Categoria;
import programacion.eCommerceApp.model.Color;
import programacion.eCommerceApp.model.Marca;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.model.Tamanio;
import programacion.eCommerceApp.repository.ICategoriaRepository;
import programacion.eCommerceApp.repository.IColorRepository;
import programacion.eCommerceApp.repository.IMarcaRepository;
import programacion.eCommerceApp.repository.IProductoRepository;
import programacion.eCommerceApp.repository.ITamanioRepository;
import programacion.eCommerceApp.service.ProductoService;

@ExtendWith(MockitoExtension.class)
public class BuscarIdProductoTest {

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
public void testBuscarPorIdEncontradoyNoNull() {
    // given
    Integer idPrueba = 1;
    
    when(productoRepository.save(productoMock)).thenReturn(productoMock);
    given(productoRepository.findById(idPrueba)).willReturn(Optional.of(productoMock));
    ProductoResponse productoResponse = ProductoMapper.toProductoResponse(productoMock);

    // when
    ResponseEntity<ProductoResponse> responseEntity = productoService.buscarPorId(idPrueba);

    // then
    ProductoResponse productoBuscado = responseEntity.getBody();
    

    verify(productoRepository, times(1)).findById(idPrueba);
}

@Test
public void testRecuperarProducto() {
    // given
    Integer idPrueba = 2;
    productoMock.setEstado(Producto.ELIMINADO);
    given(productoRepository.findById(idPrueba)).willReturn(Optional.of(productoMock));

    // when
    ResponseEntity<Void> responseEntity = productoService.recuperar(idPrueba);

    // then
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(Producto.COMUN, productoMock.getEstado());
    verify(productoRepository, times(1)).findById(idPrueba);
    verify(productoRepository, times(1)).save(productoMock);
}
}