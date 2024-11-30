// package programacion.eCommerceApp.steps;

// import io.cucumber.java.en.Given;
// import io.cucumber.java.en.When;
// import io.cucumber.java.en.Then;
// import org.junit.jupiter.api.Assertions;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.boot.test.mock.mockito.MockBean;

// import programacion.eCommerceApp.controller.request.NewProductoRequest;
// import programacion.eCommerceApp.model.Producto;
// import programacion.eCommerceApp.repository.IProductoRepository;
// import programacion.eCommerceApp.service.ProductoService;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// public class ProductoServiceSteps {

//     @MockBean
//     private IProductoRepository productoRepository;

//     @InjectMocks
//     private ProductoService productoService;

//     private NewProductoRequest newProductoRequest;
//     private Producto productoCreado;
//     private Exception exception;

//     public ProductoServiceSteps() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Given("un producto con precio {double}")
//     public void unProductoConPrecio(Double precio) {
//         newProductoRequest = new NewProductoRequest(
//                 "Producto Test",
//                 10,
//                 123456,
//                 precio,
//                 1,
//                 1,
//                 1,
//                 1
//         );
//     }

//     @When("intento crear el producto")
//     public void intentoCrearElProducto() {
//         try {
//             when(productoRepository.save(any(Producto.class)))
//                     .thenThrow(new IllegalArgumentException("El precio debe ser mayor que cero"));
//             productoService.crear(newProductoRequest);
//         } catch (Exception e) {
//             exception = e;
//         }
//     }

//     @Then("el sistema debe devolver un error indicando que el precio debe ser mayor que cero")
//     public void elSistemaDebeDevolverUnErrorIndicandoQueElPrecioDebeSerMayorQueCero() {
//         Assertions.assertNotNull(exception, "Se esperaba una excepción");
//         Assertions.assertTrue(exception instanceof IllegalArgumentException);
//         Assertions.assertEquals("El precio debe ser mayor que cero", exception.getMessage());
//     }

//     @Then("el producto debe ser creado exitosamente")
//     public void elProductoDebeSerCreadoExitosamente() {
//         Assertions.assertNotNull(productoCreado, "El producto no fue creado");
//         Assertions.assertEquals(newProductoRequest.nombre(), productoCreado.getNombre());
//         Assertions.assertEquals(newProductoRequest.precio(), productoCreado.getPrecio());
//     }
// }
