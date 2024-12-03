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
import programacion.eCommerceApp.controller.request.NewColorRequest;
import programacion.eCommerceApp.controller.response.ColorResponse;
import programacion.eCommerceApp.service.IColorService;

import java.util.List;

@RestController
@RequestMapping("ecommerce")
@CrossOrigin(value = " http://localhost:8080")

public class ColorController {

    @Autowired
    private IColorService service;

    @GetMapping({"/colores"})
    public List<ColorResponse> listar() {
        return service.listar();
    }

    @GetMapping("/colores/{id}")
    public ResponseEntity<ColorResponse> buscarPorId(@PathVariable final Integer id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/colores/existe/{nombre}")
    public ResponseEntity<ColorResponse> buscarPorNombre(@PathVariable final String nombre) {
        return service.buscarPorNombre(nombre);
    }

    @PostMapping("/colores")
    @ResponseStatus(HttpStatus.CREATED)
    public ColorResponse crear(@RequestBody @Valid final NewColorRequest newColorRequest) {
        return service.crear(newColorRequest);
    }

    @PutMapping("/colores/actualizar/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ColorResponse actualizar(@RequestBody @Valid final NewColorRequest newColorRequest, @PathVariable final Integer id) {
        return service.actualizar(newColorRequest, id);
    }

    @DeleteMapping("/colores/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable final Integer id) {
        return service.eliminar(id);
    }

    @PutMapping("/colores/recuperar/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable final Integer id) {
        return service.recuperar(id);
    }

}
