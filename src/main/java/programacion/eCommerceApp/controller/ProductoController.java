package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.service.IProductoService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ecommerce")

public class ProductoController {

    @Autowired
    private IProductoService service;

    @PostMapping("/admin/productos")
    public ProductoResponse crear(@RequestBody @Valid NewProductoRequest newProductoRequest) {
        return service.crear(newProductoRequest);
    }

    @PutMapping("/productos/actualizar/{id}")
    public ProductoResponse actualizar(@RequestBody @Valid NewProductoRequest newProductoRequest, Integer id) {
        return service.actualizar(newProductoRequest, id);
    }

    @GetMapping("/productos")
    public List<ProductoResponse> listar(){
        return service.listar();
    }

    @GetMapping("/admin/productos/auditoria")
    public List<ProductoResponse> listarParaAuditoria(){
        return service.listarParaAuditoria();
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
