package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import programacion.eCommerceApp.controller.request.NewTamanioRequest;
import programacion.eCommerceApp.controller.response.TamanioResponse;
import programacion.eCommerceApp.mapper.TamanioMapper;
import programacion.eCommerceApp.model.Tamanio;
import programacion.eCommerceApp.service.ITamanioService;

import java.util.List;

@RestController
@CrossOrigin(value="http://localhost:8080")
@RequestMapping("/eCommerce")
public class TamanioController {

    private static final Logger logger = LoggerFactory.getLogger(TamanioController.class);

    @Autowired
    private ITamanioService modelService;

    @PostMapping("/tamanio")
    @ResponseStatus(HttpStatus.CREATED)
    public TamanioResponse crear(@RequestBody @Valid NewTamanioRequest newTamanioRequest) {
        return modelService.crear(newTamanioRequest);
    }

    @GetMapping("/tamanio")
    public List<TamanioResponse> getAll() {
        logger.info("Entra y trae todos los tamaños.");
        return modelService.listar();
    }

    @GetMapping("/tamanio/recuperar/{id}")
    public ResponseEntity<TamanioResponse> buscarPorId(@PathVariable Integer id) {
        Tamanio model = modelService.buscarPorId(id);

        //uso la constante "ELIMINADO" para comparar y no el valor
        if (model == null || model.getEstado() == Tamanio.ELIMINADO) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ID: "+id);
        }

        TamanioResponse tamanioResponse = TamanioMapper.toTamanioResponse(model);
        return ResponseEntity.ok(tamanioResponse);
    }

    @PutMapping("/tamanio")
    public TamanioResponse actualizar(@RequestBody @Valid NewTamanioRequest newTamanioRequest) {
        return modelService.crear(newTamanioRequest);
    }

    @PutMapping("/tamanio/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable Integer id) {
        Tamanio model = modelService.buscarPorId(id);

        if (model == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ID: "+id);
        }

        modelService.recuperar(model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tamanio/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Tamanio model = modelService.buscarPorId(id);

        if (model == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ID: "+id);
        }

        modelService.eliminar(model);
        return ResponseEntity.ok().build();
    }
}
