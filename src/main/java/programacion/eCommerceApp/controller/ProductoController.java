package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.mapper.ProductoMapper;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.service.IProductoService;

import java.util.List;

@RestController
@RequestMapping("/eCommerce")
@CrossOrigin(value=" http://localhost:8080")

public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(MarcaController.class);

    @Autowired
    private IProductoService modelService;

    @PostMapping("/producto")
    public ProductoResponse crear(@RequestBody @Valid NewProductoRequest newProductoRequest){
        return modelService.crear(newProductoRequest);
    }

    @GetMapping("/producto")
    public List<ProductoResponse> getAll(){
        logger.info("Entra y trae todos los productos.");
        return modelService.listar();
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<ProductoResponse> buscarPorId(@PathVariable Integer id){
        Producto model = modelService.buscarPorId(id);

        if(model == null || model.getEstado() == 1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ID: "+id);
        }
        ProductoResponse productoResponse = ProductoMapper.toProductoResponse(model);
        return ResponseEntity.ok(productoResponse);
    }

    @PutMapping("/producto")
    public ProductoResponse actualizar(@RequestBody @Valid NewProductoRequest newProductoRequest){
        return modelService.crear(newProductoRequest);
    }

    @PutMapping("/producto/recuperar/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable Integer id) {
        Producto model = modelService.buscarPorId(id);

        if(model == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ID: "+id);
        }
        modelService.recuperar(model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/producto/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        Producto model = modelService.buscarPorId(id);

        if (model == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ID: "+id);
        }
        modelService.eliminar(model);
        return ResponseEntity.ok().build();
    }
}
