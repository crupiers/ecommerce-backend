package programacion.eCommerceApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import programacion.eCommerceApp.model.Categoria;
import programacion.eCommerceApp.model.Color;
import programacion.eCommerceApp.model.Marca;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.model.Tamanio;
import programacion.eCommerceApp.repository.IProductoRepository;

@SpringBootTest
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;

    @MockBean
    private IProductoRepository productoRepository;

    @BeforeEach
    void setUp() {
        Color color = Color.builder().id(1).nombre("Rojo").build();
        Tamanio tamanio = Tamanio.builder().id(1).denominacion("Grande").build();
        Categoria categoria = Categoria.builder().id(1).nombre("Electrónica").build();
        Marca marca = Marca.builder().id(1).denominacion("Samsung").build();
        Producto producto = Producto.builder()
            .id(1)
            .nombre("Heladera")
            .stock(10)
            .codigoBarra(12)
            .precio(1000.0)
            .color(color)
            .tamanio(tamanio)
            .categoria(categoria)
            .marca(marca)
            .build();
        Mockito.when(productoRepository.findByNombre("Heladera")).thenReturn(Optional.of(producto));
    }

    @Test
    public void findProductoByNombre() {
        String nombrePrueba = "Heladera";
        Producto productoBuscadoPorNombre = productoService.buscarPorNombre(nombrePrueba);
        assertEquals(nombrePrueba, productoBuscadoPorNombre.getNombre());
    }
}
