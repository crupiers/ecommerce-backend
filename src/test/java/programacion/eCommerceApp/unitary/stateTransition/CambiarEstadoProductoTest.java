package programacion.eCommerceApp.unitary.stateTransition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import programacion.eCommerceApp.model.Categoria;
import programacion.eCommerceApp.model.Color;
import programacion.eCommerceApp.model.Marca;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.model.Tamanio;
import programacion.eCommerceApp.repository.IProductoRepository;
import programacion.eCommerceApp.service.ProductoService;
/*
 * Se prueba:
 * - Eliminar un producto existente
 * - Eliminar un producto no existente
 * - Eliminar un producto eliminado
 * - Recuperar un producto eliminado
 * - Recuperar un producto no existente
 * - Recuperar un producto no eliminado
 */
@ExtendWith(MockitoExtension.class)
public class CambiarEstadoProductoTest {

@Mock
private IProductoRepository productoRepository;

@InjectMocks
private ProductoService productoService;

private Integer idPrueba;
private Producto productoMock;

@BeforeEach
public void setUp() {
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
public void eliminarProductoExistente() {
    // given
    when(productoRepository.findById(idPrueba)).thenReturn(Optional.of(productoMock));
    when(productoRepository.save(productoMock)).thenReturn(productoMock);

    // when
    ResponseEntity<Void> response = productoService.eliminar(productoMock.getId());

    // then
    assertNotNull(response);
    // assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Producto.ELIMINADO, productoMock.getEstado());

    verify(productoRepository, times(1)).findById(idPrueba);
    verify(productoRepository, times(1)).save(productoMock);
}
@Test
public void testEliminarProductoNoExistente() {
    // given
    when(productoRepository.findById(idPrueba)).thenReturn(Optional.empty());

    // when
    assertThrows(ResponseStatusException.class, () -> productoService.eliminar(idPrueba));
}
@Test
public void testEliminarProductoEliminado() {
    // given
    productoMock.setEstado(Producto.ELIMINADO);
    when(productoRepository.findById(idPrueba)).thenReturn(Optional.of(productoMock));

    // when
    assertThrows(ResponseStatusException.class, () -> productoService.eliminar(idPrueba));
}

@Test
public void testRecuperarProductoEliminado() {
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
@Test
public void testRecuperarProductoNoExistente() {
    // given
    when(productoRepository.findById(idPrueba)).thenReturn(Optional.empty());

    // when
    assertThrows(ResponseStatusException.class, () -> productoService.recuperar(idPrueba));
}

@Test
public void testRecuperarProductoNoEliminado() {
    // given
    when(productoRepository.findById(idPrueba)).thenReturn(Optional.of(productoMock));

    // when
    assertThrows(ResponseStatusException.class, () -> productoService.recuperar(idPrueba));
}
}