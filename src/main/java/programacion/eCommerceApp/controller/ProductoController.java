package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.service.IProductoService;
import java.util.List;

@RestController
@RequestMapping("/ecommerce")
@CrossOrigin(value = " http://localhost:8080")

public class ProductoController {

    @Autowired
    private IProductoService service;

    @PostMapping("/productos")
    public ProductoResponse crear(@RequestBody @Valid final NewProductoRequest newProductoRequest) {
        return service.crear(newProductoRequest);
    }

    @PutMapping("/productos/actualizar/{id}")
    public ProductoResponse actualizar(@RequestBody @Valid final NewProductoRequest newProductoRequest, final Integer id) {
        return service.actualizar(newProductoRequest, id);
    }

    @GetMapping("/productos")
    public List<ProductoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoResponse> buscarPorId(@PathVariable final Integer id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable final Integer id) {
        return service.eliminar(id);
    }

    @PutMapping("/productos/recuperar/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable final Integer id) {
        return service.recuperar(id);
    }

}
