package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.eCommerceApp.controller.request.NewTamanioRequest;
import programacion.eCommerceApp.controller.response.TamanioResponse;
import programacion.eCommerceApp.service.ITamanioService;
import programacion.eCommerceApp.service.TamanioService;

import java.util.List;

@RestController
@CrossOrigin(value="http://localhost:8080")
@RequestMapping("/ecommerce")
public class TamanioController {

    @Autowired
    private ITamanioService service;
    @Autowired
    private TamanioService tamanioService;

    @PostMapping("/admin/tamanios")
    @ResponseStatus(HttpStatus.CREATED)
    public TamanioResponse crear(@RequestBody @Valid NewTamanioRequest newTamanioRequest) {
        return service.crear(newTamanioRequest);
    }

    @GetMapping("/tamanios")
    public List<TamanioResponse> listar() {
        return service.listar();
    }

    @GetMapping("/admin/tamanios/auditoria")
    public List<TamanioResponse> listarParaAuditoria() {
        return service.listarParaAuditoria();
    }

    @GetMapping("/tamanios/{id}")
    public ResponseEntity<TamanioResponse> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/tamanios/actualizar/{id}")
    public TamanioResponse actualizar(@RequestBody @Valid NewTamanioRequest newTamanioRequest,  @PathVariable Integer id) {
        return service.actualizar(newTamanioRequest , id);
    }

    @DeleteMapping("/tamanios/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return tamanioService.eliminar(id);
    }

    @PutMapping("/tamanios/recuperar/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable Integer id) {
        return tamanioService.recuperar(id);
    }

}
