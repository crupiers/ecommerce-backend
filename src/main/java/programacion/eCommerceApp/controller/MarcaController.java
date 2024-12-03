package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import programacion.eCommerceApp.controller.request.NewMarcaRequest;
import programacion.eCommerceApp.controller.response.MarcaResponse;
import programacion.eCommerceApp.service.IMarcaService;
import java.util.List;

@RestController
@RequestMapping("/ecommerce")
@CrossOrigin(value = " http://localhost:8080")

public class MarcaController {

    @Autowired
    private IMarcaService service;

    @GetMapping({"/marcas"})
    public List<MarcaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/marcas/{id}")
    public ResponseEntity<MarcaResponse> buscarPorId(@PathVariable final Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping("/marcas")
    public MarcaResponse crear(@RequestBody @Valid final NewMarcaRequest newMarcaRequest) {
        return service.crear(newMarcaRequest);
    }

    @PutMapping("/marcas/actualizar/{id}")
    public MarcaResponse actualizar(@RequestBody @Valid final NewMarcaRequest newMarcaRequest, @PathVariable final Integer id) {
        return service.actualizar(newMarcaRequest, id);
    }

    @DeleteMapping("/marcas/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable final Integer id) {
        return service.eliminar(id);
    }

    @PutMapping("/marcas/recuperar/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable final Integer id) {
        return service.recuperar(id);
    }

}
