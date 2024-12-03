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
import programacion.eCommerceApp.controller.request.NewCategoriaRequest;
import programacion.eCommerceApp.controller.response.CategoriaResponse;
import programacion.eCommerceApp.service.ICategoriaService;
import java.util.List;

@RestController
@RequestMapping("/ecommerce")
@CrossOrigin(value = " http://localhost:8080")

public class CategoriaController {

    @Autowired
    private ICategoriaService service;

    @GetMapping("/categorias")
    public List<CategoriaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponse> buscarPorId(@PathVariable final Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping("/categorias")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponse crear(@RequestBody @Valid final NewCategoriaRequest newCategoriaRequest) {
        return service.crear(newCategoriaRequest);
    }

    @PutMapping("/categorias/actualizar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponse actualizar(@RequestBody @Valid final NewCategoriaRequest newCategoriaRequest, @PathVariable final Integer id) {
        return service.actualizar(newCategoriaRequest, id);
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable final Integer id) {
        return service.eliminar(id);
    }

    @PutMapping("/categorias/recuperar/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable final Integer id) {
        return service.recuperar(id);
    }
}
