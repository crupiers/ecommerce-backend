package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.eCommerceApp.controller.request.NewTamanioRequest;
import programacion.eCommerceApp.controller.response.TamanioResponse;
import programacion.eCommerceApp.service.ITamanioService;
import java.util.List;

@RestController
@CrossOrigin(value="http://localhost:8080")
@RequestMapping("/ecommerce")
public class TamanioController {

    @Autowired
    private ITamanioService modelService;

    @PostMapping("/tamanios")
    @ResponseStatus(HttpStatus.CREATED)
    public TamanioResponse crear(@RequestBody @Valid NewTamanioRequest newTamanioRequest) {
        return modelService.crear(newTamanioRequest);
    }

    @GetMapping("/tamanios")
    public List<TamanioResponse> listar() {
        return modelService.listar();
    }

    @GetMapping("/tamanios/recuperar/{id}")
    public ResponseEntity<TamanioResponse> buscarPorId(@PathVariable Integer id) {
        return modelService.buscarPorId(id);
    }

    @PutMapping("/tamanios/actualizar/{id}")
    public TamanioResponse actualizar(@RequestBody @Valid NewTamanioRequest newTamanioRequest,  @PathVariable Integer id) {
        return modelService.actualizar(newTamanioRequest , id);
    }

    @DeleteMapping("/tamanios/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return modelService.eliminar(id);
    }

    @PutMapping("/tamanios/recuperar/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable Integer id) {
        return modelService.recuperar(id);
    }

}
