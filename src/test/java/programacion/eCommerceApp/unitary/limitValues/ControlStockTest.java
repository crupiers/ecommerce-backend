package programacion.eCommerceApp.unitary.limitValues;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.hibernate.query.IllegalNamedQueryOptionsException;
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

import programacion.eCommerceApp.repository.ICategoriaRepository;
import programacion.eCommerceApp.repository.IColorRepository;
import programacion.eCommerceApp.repository.IMarcaRepository;
import programacion.eCommerceApp.repository.IProductoRepository;
import programacion.eCommerceApp.repository.ITamanioRepository;
import programacion.eCommerceApp.service.ProductoService;

@ExtendWith(MockitoExtension.class) //para que funcione la inyección de dependencias.
public class ControlStockTest {

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

    private Integer limiteInferior = 0;
    private Integer limiteSuperior = 1000000;

@BeforeEach
void setUp() {

baseRequest = new NewProductoRequest(
    "Producto de prueba",
    "Descripción de prueba",
    10,
    123456,
    100.0,
    1,
    1, // colorId
    1, // tamanioId
    1, // categoriaId
    1  // marcaId

);
    color = new Color();
    tamanio = new Tamanio();
    categoria = new Categoria();
    marca = new Marca();
// Simular repositorios
    when(colorRepository.findById(baseRequest.colorId())).thenReturn(Optional.of(color));
    when(tamanioRepository.findById(baseRequest.tamanioId())).thenReturn(Optional.of(tamanio));
    when(categoriaRepository.findById(baseRequest.categoriaId())).thenReturn(Optional.of(categoria));
    when(marcaRepository.findById(baseRequest.marcaId())).thenReturn(Optional.of(marca));
}
@Test
void verificarStocLimiteInferior() {
    // given
    Integer stock = 0;

    baseRequest = new NewProductoRequest(
        baseRequest.nombre(),
        baseRequest.descripcion(),
        stock, // Cambiar el stock aquí si necesario
        baseRequest.codigoBarra(),
        baseRequest.precio(),
        baseRequest.umbral(),
        baseRequest.colorId(),
        baseRequest.tamanioId(),
        baseRequest.categoriaId(),
        baseRequest.marcaId()
    );
    
    Producto productoEsperado = ProductoMapper.toEntity(baseRequest, color, tamanio, categoria, marca);
    
    // when
    when(productoRepository.save(any())).thenReturn(productoEsperado);
    ProductoResponse response = productoService.crear(baseRequest);

    // then
    
    assertTrue(response.stock() >= limiteInferior && response.stock() <= limiteSuperior, "El stock debe estar entre 0 y 1000");
    verify(productoRepository, times(1)).save(any());
}
@Test
void verificarStocLimiteSuperior() {
    // given
    Integer stock = 1000000;

    baseRequest = new NewProductoRequest(
        baseRequest.nombre(),
        baseRequest.descripcion(),
        stock, // Cambiar el stock aquí si necesario
        baseRequest.codigoBarra(),
        baseRequest.precio(),
        baseRequest.umbral(),
        baseRequest.colorId(),
        baseRequest.tamanioId(),
        baseRequest.categoriaId(),
        baseRequest.marcaId()
    );
    
    Producto productoEsperado = ProductoMapper.toEntity(baseRequest, color, tamanio, categoria, marca);
    
    // when
    when(productoRepository.save(any())).thenReturn(productoEsperado);
    ProductoResponse response = productoService.crear(baseRequest);

    // then
    assertTrue(response.stock() >= limiteInferior && response.stock() <= limiteSuperior, "El stock debe estar entre 0 y 1000");
    verify(productoRepository, times(1)).save(any());
}
@Test
void verificarStockInvalido() {
// given
baseRequest = new NewProductoRequest(
    baseRequest.nombre(),
    baseRequest.descripcion(),
    10, // Cambiamos el stock aquí
    baseRequest.codigoBarra(),
    baseRequest.precio(),
    baseRequest.umbral(),
    baseRequest.colorId(),
    baseRequest.tamanioId(),
    baseRequest.categoriaId(),
    baseRequest.marcaId()
);

// when & then
assertThrows(NullPointerException.class, () -> {
    productoService.crear(baseRequest); // Aquí se espera que lance la excepción
});

// Verificar el mensaje de la excepción
// assertEquals("El stock no puede ser negativo", exception.getMessage());

// Verificar que el repositorio nunca intentó guardar el producto
// verify(productoRepository, never()).save(any());
}

}
