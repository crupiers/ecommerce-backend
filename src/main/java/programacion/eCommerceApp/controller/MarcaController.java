package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.eCommerceApp.controller.request.NewMarcaRequest;
import programacion.eCommerceApp.controller.response.MarcaResponse;
import programacion.eCommerceApp.service.IMarcaService;
import java.util.List;

@RestController
@RequestMapping("/ecommerce")
@CrossOrigin(value=" http://localhost:8080")

public class MarcaController {

    @Autowired
    private IMarcaService modelService;

    @GetMapping({"/marcas"})
    public List<MarcaResponse> listar() {
        return modelService.listar();
    }

    @GetMapping("/marcas/{id}")
    public ResponseEntity<MarcaResponse> buscarPorId(@PathVariable Integer id){
        return modelService.buscarPorId(id);
    }
    
    @PostMapping("/marcas")
    public MarcaResponse crear(@RequestBody @Valid NewMarcaRequest newMarcaRequest){
        return modelService.crear(newMarcaRequest);
    }

    @PutMapping("/marcas/actualizar/{id}")
    public MarcaResponse actualizar(@RequestBody @Valid NewMarcaRequest newMarcaRequest, @PathVariable Integer id){
        return modelService.actualizar(newMarcaRequest, id);
    }

    @DeleteMapping("/marcas/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return modelService.eliminar(id);
    }

    @PutMapping("/marcas/recuperar/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable Integer id) {
        return modelService.recuperar(id);
    }

}
