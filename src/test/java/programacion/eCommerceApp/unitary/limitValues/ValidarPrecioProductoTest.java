package programacion.eCommerceApp.unitary.limitValues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

/*
 * Esta clase verifica que cuando se cree un producto con precio entre el limite inferior y superior, el precio se guarde correctamente.
 */

@ExtendWith(MockitoExtension.class)
public class ValidarPrecioProductoTest {
    @Mock
    private IProductoRepository productoRepository;
    @Mock
    private IColorRepository colorRepository;
    @Mock
    private ITamanioRepository tamanioRepository;
    @Mock
    private ICategoriaRepository categoriaRepository;
    @Mock
    private IMarcaRepository marcaRepository;
    
    @InjectMocks
    private ProductoService productoService;
    
    private NewProductoRequest baseRequest;

    private Color color;
    private Tamanio tamanio;
    private Categoria categoria;
    private Marca marca;

    private Double precioLimiteInferior = 1.00;
    private Double precioLimiteSuperior = 100000000.00;

    @BeforeEach
        void setUp() {
        MockitoAnnotations.openMocks(this);
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
    public void asignarPrecioValido() {
        Double precio = precioLimiteInferior;

        baseRequest = new NewProductoRequest(
        baseRequest.nombre(),
        baseRequest.descripcion(),
        baseRequest.stock(), // Limite inferior
        baseRequest.codigoBarra(),
        precio,
        baseRequest.umbral(),
        baseRequest.colorId(),
        baseRequest.tamanioId(),
        baseRequest.categoriaId(),
        baseRequest.marcaId()
        );
        
        Producto productoEsperado = ProductoMapper.toEntity(baseRequest, color, tamanio, categoria, marca);
        when(productoRepository.save(any())).thenReturn(productoEsperado);
        // when
        ProductoResponse response = productoService.crear(baseRequest);
        
        productoService.crear(baseRequest);
        // Then
        assertEquals(1.00, response.precio());
    }
    @Test
    public void asignarPrecioCero() {
        // Given
        Double precio = 0.00;

        baseRequest = new NewProductoRequest(
        baseRequest.nombre(),
        baseRequest.descripcion(),
        baseRequest.stock(), // Limite inferior
        baseRequest.codigoBarra(),
        precio,
        baseRequest.umbral(),
        baseRequest.colorId(),
        baseRequest.tamanioId(),
        baseRequest.categoriaId(),
        baseRequest.marcaId()
        );
        
        // when
        assertThrows(IllegalArgumentException.class, () -> productoService.crear(baseRequest));

    }
    @Test
    public void asignarPrecioNegativo() {
        // Given
        Double precio = -1.00;

        baseRequest = new NewProductoRequest(
        baseRequest.nombre(),
        baseRequest.descripcion(),
        baseRequest.stock(), // Limite inferior
        baseRequest.codigoBarra(),
        precio,
        baseRequest.umbral(),
        baseRequest.colorId(),
        baseRequest.tamanioId(),
        baseRequest.categoriaId(),
        baseRequest.marcaId()
        );
        
        // Then
        assertThrows(IllegalArgumentException.class, () -> productoService.crear(baseRequest));
    }
    @Test
    public void asignarPrecioAltoValido() {
        // Given
        Double precio = precioLimiteSuperior;

        baseRequest = new NewProductoRequest(
        baseRequest.nombre(),
        baseRequest.descripcion(),
        baseRequest.stock(), // Limite inferior
        baseRequest.codigoBarra(),
        precio,
        baseRequest.umbral(),
        baseRequest.colorId(),
        baseRequest.tamanioId(),
        baseRequest.categoriaId(),
        baseRequest.marcaId()
        );
        
        Producto productoEsperado = ProductoMapper.toEntity(baseRequest, color, tamanio, categoria, marca);
        when(productoRepository.save(any())).thenReturn(productoEsperado);
        // when
        ProductoResponse response = productoService.crear(baseRequest);
        // When
        productoService.crear(baseRequest);
        // Then
        assertEquals(precioLimiteSuperior, response.precio());
    }
    @Test
    public void asignarPrecioAltoInvalido() {
        // Given
        Double precio = precioLimiteSuperior + 1.00;

        baseRequest = new NewProductoRequest(
        baseRequest.nombre(),
        baseRequest.descripcion(),
        baseRequest.stock(), // Limite inferior
        baseRequest.codigoBarra(),
        precio,
        baseRequest.umbral(),
        baseRequest.colorId(),
        baseRequest.tamanioId(),
        baseRequest.categoriaId(),
        baseRequest.marcaId()
        );

        // Then
        assertThrows(IllegalArgumentException.class, () -> productoService.crear(baseRequest));
    }

}
