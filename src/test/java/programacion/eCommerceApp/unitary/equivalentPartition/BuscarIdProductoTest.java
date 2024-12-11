package programacion.eCommerceApp.unitary.equivalentPartition;

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
import programacion.eCommerceApp.mapper.ProductoMapper;
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

/*
 Esta clase verifica que cuando se busque un producto por su id, se encuentre y no sea nulo.
 */
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
void testBuscarPorIdEncontradoyNoNull() {
    // given
    Integer idPrueba = 1;
    

    given(productoRepository.findById(idPrueba)).willReturn(Optional.of(productoMock));
    ProductoResponse productoResponse = ProductoMapper.toProductoResponse(productoMock);

    // when
    ResponseEntity<ProductoResponse> responseEntity = productoService.buscarPorId(idPrueba);

    // then
    ProductoResponse productoBuscado = responseEntity.getBody();
    assertNotNull(productoBuscado);

    assertAll("producto",
        () -> assertEquals(productoResponse.id(), productoBuscado.id()),
        () -> assertEquals(productoResponse.nombre(), productoBuscado.nombre()),
        () -> assertEquals(productoResponse.descripcion(), productoBuscado.descripcion()),
        () -> assertEquals(productoResponse.precio(), productoBuscado.precio()),
        () -> assertEquals(productoResponse.umbral(), productoBuscado.umbral()),
        () -> assertEquals(productoResponse.stock(), productoBuscado.stock()),
        () -> assertEquals(productoResponse.nombreCategoria(), productoBuscado.nombreCategoria()),
        () -> assertEquals(productoResponse.nombreMarca(), productoBuscado.nombreMarca()),
        () -> assertEquals(productoResponse.nombreTamanio(), productoBuscado.nombreTamanio()),
        () -> assertEquals(productoResponse.nombreColor(), productoBuscado.nombreColor()),
        () -> assertEquals(productoResponse.codigoBarra(), productoBuscado.codigoBarra())
    );

    verify(productoRepository, times(1)).findById(idPrueba);
}

@Test
void testBuscarPorIdEncontradoEliminado() {
    // given
    Integer idPrueba = 1;
    productoMock.setEstado(Producto.ELIMINADO);
    given(productoRepository.findById(idPrueba)).willReturn(Optional.of(productoMock));

    // when & then
    assertThrows(ResponseStatusException.class, () -> {
        productoService.buscarPorId(idPrueba);
    });

    verify(productoRepository, times(1)).findById(idPrueba);
}

@Test
void testBuscarPorIdNoEncontrado() {
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