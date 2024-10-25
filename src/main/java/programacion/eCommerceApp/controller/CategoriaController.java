package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import programacion.eCommerceApp.controller.request.NewCategoriaRequest;
import programacion.eCommerceApp.controller.response.CategoriaResponse;
import programacion.eCommerceApp.service.ICategoriaService;

import java.util.List;

@RestController
@RequestMapping("/ecommerce")
@CrossOrigin(value=" http://localhost:8080")

public class CategoriaController {

    @Autowired
    private ICategoriaService modelService;

    @GetMapping("/categorias")
    public List<CategoriaResponse> listar() {
        return modelService.listar();
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponse> buscarPorId(@PathVariable Integer id){
        return modelService.buscarPorId(id);
    }

    @PostMapping("/categorias")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponse crear(@RequestBody @Valid NewCategoriaRequest newCategoriaRequest){
        return modelService.crear(newCategoriaRequest);
    }

    @PutMapping("/categorias/actualizar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponse actualizar(@RequestBody @Valid NewCategoriaRequest newCategoriaRequest, @PathVariable Integer id){
        return modelService.actualizar(newCategoriaRequest, id);
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return modelService.eliminar(id);
    }

    @PutMapping("/categorias/recuperar/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable Integer id) {
        return modelService.recuperar(id);
    }
}
