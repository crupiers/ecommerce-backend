package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.eCommerceApp.controller.request.NewColorRequest;
import programacion.eCommerceApp.controller.response.ColorResponse;
import programacion.eCommerceApp.service.IColorService;

import java.util.List;

@RestController 
@RequestMapping("ecommerce")
@CrossOrigin(value=" http://localhost:8080")

public class ColorController {

    @Autowired
    private IColorService modelService;

    @GetMapping({"/colores"})
    public List<ColorResponse> listar(){
        return modelService.listar();
    }

    @GetMapping("/colores/{id}")
    public ResponseEntity<ColorResponse> buscarPorId(@PathVariable Integer id){
        return modelService.buscarPorId(id);
    }

    @GetMapping("/colores/existe/{nombre}")
    public ResponseEntity<ColorResponse> buscarPorNombre(@PathVariable String nombre){
        return modelService.buscarPorNombre(nombre);
    }

    @PostMapping("/colores")
    @ResponseStatus(HttpStatus.CREATED)
    public ColorResponse crear(@RequestBody @Valid NewColorRequest newColorRequest){
        return modelService.crear(newColorRequest);
    }

    @PutMapping("/colores/actualizar/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ColorResponse actualizar(@RequestBody @Valid NewColorRequest newColorRequest, @PathVariable Integer id){
        return modelService.actualizar(newColorRequest, id);
    }

    @DeleteMapping("/colores/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        return modelService.eliminar(id);
    }

    @PutMapping("/colores/recuperar/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable Integer id){
        return modelService.recuperar(id);
    }

}
