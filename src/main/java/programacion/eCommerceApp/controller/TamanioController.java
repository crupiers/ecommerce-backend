package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import programacion.eCommerceApp.controller.request.NewTamanioRequest;
import programacion.eCommerceApp.controller.response.TamanioResponse;
import programacion.eCommerceApp.service.ITamanioService;
import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:8080")
@RequestMapping("/ecommerce")
public class TamanioController {

    @Autowired
    private ITamanioService service;

    @PostMapping("/tamanios")
    @ResponseStatus(HttpStatus.CREATED)
    public TamanioResponse crear(@RequestBody @Valid final NewTamanioRequest newTamanioRequest) {
        return service.crear(newTamanioRequest);
    }

    @GetMapping("/tamanios")
    public List<TamanioResponse> listar() {
        return service.listar();
    }

    @GetMapping("/tamanios/recuperar/{id}")
    public ResponseEntity<TamanioResponse> buscarPorId(@PathVariable final Integer id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/tamanios/actualizar/{id}")
    public TamanioResponse actualizar(@RequestBody @Valid final NewTamanioRequest newTamanioRequest,  @PathVariable final Integer id) {
        return service.actualizar(newTamanioRequest, id);
    }

    @DeleteMapping("/tamanios/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable final Integer id) {
        return service.eliminar(id);
    }

    @PutMapping("/tamanios/recuperar/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable final Integer id) {
        return service.recuperar(id);
    }

}
