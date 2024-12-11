package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.eCommerceApp.controller.request.NewColorRequest;
import programacion.eCommerceApp.controller.response.ColorResponse;
import programacion.eCommerceApp.model.Color;
import programacion.eCommerceApp.service.IColorService;

import java.util.List;

@RestController 
@RequestMapping("ecommerce")
@CrossOrigin(value=" http://localhost:8080")

public class ColorController {

    @Autowired
    private IColorService service;

    @GetMapping({"/colores"})
    public List<ColorResponse> listar(){
        return service.listar();
    }

    @GetMapping("/colores/auditoria")
    public List<ColorResponse> listarParaAuditoria(){ return service.listarParaAuditoria();}

    @GetMapping("/colores/{id}")
    public ResponseEntity<ColorResponse> buscarPorId(@PathVariable Integer id){
        return service.buscarPorId(id);
    }

    @GetMapping("/colores/existe/{nombre}")
    public ResponseEntity<ColorResponse> buscarPorNombre(@PathVariable String nombre){
        return service.buscarPorNombre(nombre);
    }

    @PostMapping("/colores")
    @ResponseStatus(HttpStatus.CREATED)
    public ColorResponse crear(@RequestBody @Valid NewColorRequest newColorRequest){
        return service.crear(newColorRequest);
    }

    @PutMapping("/colores/actualizar/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ColorResponse actualizar(@RequestBody @Valid NewColorRequest newColorRequest, @PathVariable Integer id){
        return service.actualizar(newColorRequest, id);
    }

    @DeleteMapping("/colores/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        return service.eliminar(id);
    }

    @PutMapping("/colores/recuperar/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable Integer id){
        return service.recuperar(id);
    }

}
