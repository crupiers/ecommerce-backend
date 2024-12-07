package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.service.IProductoService;
import java.util.List;

@RestController
@RequestMapping("/ecommerce")
@CrossOrigin(value=" http://localhost:8080")

public class ProductoController {

    @Autowired
    private IProductoService service;

    @PostMapping("/productos")
    public ProductoResponse crear(@RequestBody @Valid NewProductoRequest newProductoRequest){
        return service.crear(newProductoRequest);
    }

    @PutMapping("/productos/actualizar/{id}")
    public ProductoResponse actualizar(@RequestBody @Valid NewProductoRequest newProductoRequest , Integer id){
        return service.actualizar(newProductoRequest , id);
    }

    @GetMapping("/productos")
    public List<ProductoResponse> listar(){
        return service.listar();
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoResponse> buscarPorId(@PathVariable Integer id){
        return service.buscarPorId(id);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        return service.eliminar(id);
    }

    @PutMapping("/productos/recuperar/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable Integer id) {
       return service.recuperar(id);
    }

}
