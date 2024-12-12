package programacion.eCommerceApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.mapper.ProductoMapper;
import programacion.eCommerceApp.model.Categoria;
import programacion.eCommerceApp.model.Color;
import programacion.eCommerceApp.model.Marca;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.model.Tamanio;
import programacion.eCommerceApp.repository.IColorRepository;
import programacion.eCommerceApp.repository.IProductoRepository;
import programacion.eCommerceApp.repository.ITamanioRepository;
import programacion.eCommerceApp.repository.ICategoriaRepository;
import programacion.eCommerceApp.repository.IMarcaRepository;
import programacion.eCommerceApp.service.ProductoService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class GuardarProductoStockTest {

@Mock 
IProductoRepository productoRepository;
@Mock
IColorRepository colorRepository;
@Mock
ITamanioRepository tamanioRepository;
@Mock
ICategoriaRepository categoriaRepository;
@Mock
IMarcaRepository marcaRepository;

@InjectMocks
ProductoService productoService;

private NewProductoRequest baseRequest;

private Color color;
private Tamanio tamanio;
private Categoria categoria;
private Marca marca;

@BeforeEach
void setUp() {
    baseRequest = new NewProductoRequest(
        "Producto de prueba",
        "Descripción de prueba",
        10,
        123456,
        100.0,
        10, // umbral
        1, // colorId
        1, // tamanioId
        1, // categoriaId
        1  // marcaId
    );

    when(colorRepository.findById(baseRequest.colorId())).thenReturn(Optional.of(color));
    when(tamanioRepository.findById(baseRequest.tamanioId())).thenReturn(Optional.of(tamanio));
    when(categoriaRepository.findById(baseRequest.categoriaId())).thenReturn(Optional.of(categoria));
    when(marcaRepository.findById(baseRequest.marcaId())).thenReturn(Optional.of(marca));
}

@Test
void verificarStockPermitido() {
    
    // Simular repositorios
    Producto productoEsperado = ProductoMapper.toEntity(baseRequest, color, tamanio, categoria, marca);

    // Simular guardar el producto
    when(productoRepository.save(any())).thenReturn(productoEsperado);

    // Ejecutar el servicio
    ProductoResponse response = productoService.crear(baseRequest);

    // Verificar que el producto se creó con los valores esperados
    assertEquals(baseRequest.stock(), response.stock());
}


@Test
public void verificarStockInvalido() {
    // given
    Integer stock = -1;
    baseRequest = new NewProductoRequest(
        baseRequest.nombre(),
        baseRequest.descripcion(),
        stock,
        baseRequest.codigoBarra(),
        baseRequest.precio(),
        baseRequest.umbral(),
        baseRequest.colorId(),
        baseRequest.tamanioId(),
        baseRequest.categoriaId(),
        baseRequest.marcaId()
    );

    // when & then
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        productoService.crear(baseRequest); // Aquí se espera que lance la excepción
    });

    // Verificar el mensaje de la excepción
    assertEquals("El stock no puede ser negativo", exception.getMessage());

    // Verificar que el repositorio nunca intentó guardar el producto
    verify(productoRepository, never()).save(any());
}



}
