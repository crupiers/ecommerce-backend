package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import programacion.eCommerceApp.controller.request.NewTamañoRequest;
import programacion.eCommerceApp.controller.response.TamañoResponse;
import programacion.eCommerceApp.mapper.TamañoMapper;
import programacion.eCommerceApp.model.Tamaño;
import programacion.eCommerceApp.service.ITamañoService;

import java.util.List;

@RestController
@CrossOrigin(value="http://localhost:8080")
@RequestMapping("/eCommerce")
public class TamañoController {

    private static final Logger logger = LoggerFactory.getLogger(TamañoController.class);

    @Autowired
    private ITamañoService modelService;

    @PostMapping("/tamaño")
    @ResponseStatus(HttpStatus.CREATED)
    public TamañoResponse crear(@RequestBody @Valid NewTamañoRequest newTamañoRequest) {
        return modelService.crear(newTamañoRequest);
    }

    @GetMapping("/tamaño")
    public List<TamañoResponse> getAll() {
        logger.info("Entra y trae todos los tamaños.");
        return modelService.listar();
    }

    @GetMapping("/tamaño/{id}")
    public ResponseEntity<TamañoResponse> buscarPorId(@PathVariable Integer id) {
        Tamaño model = modelService.buscarPorId(id);

        if (model == null) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ID: "+id);
        }

        TamañoResponse tamañoResponse = TamañoMapper.toTamañoResponse(model);
        return ResponseEntity.ok(tamañoResponse);
    }

    @PutMapping("/tamaño")
    public TamañoResponse actualizar(@RequestBody @Valid NewTamañoRequest newTamañoRequest) {
        return modelService.crear(newTamañoRequest);
    }

    @PutMapping("/tamaño/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable Integer id) {
        Tamaño model = modelService.buscarPorId(id);

        if (model == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ID: "+id);
        }

        modelService.recuperar(model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tamaño/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Tamaño model = modelService.buscarPorId(id);

        if (model == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ID: "+id);
        }

        modelService.eliminar(model);
        return ResponseEntity.ok().build();
    }
}
