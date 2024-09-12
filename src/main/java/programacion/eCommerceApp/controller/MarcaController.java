package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import programacion.eCommerceApp.controller.request.NewMarcaRequest;
import programacion.eCommerceApp.controller.response.MarcaResponse;
import programacion.eCommerceApp.mapper.MarcaMapper;
import programacion.eCommerceApp.exception.RecursoNoEncontradoExcepcion;
import programacion.eCommerceApp.model.Marca;
import programacion.eCommerceApp.service.IMarcaService;

import java.util.List;

@RestController
@RequestMapping("/eCommerce")
@CrossOrigin(value=" http://localhost:8080")

public class MarcaController {

    private static final Logger logger = LoggerFactory.getLogger(MarcaController.class);

    @Autowired
    private IMarcaService modelService;

    @GetMapping({"/marca"})
    public List<MarcaResponse> getAll() {
        logger.info("Entra y trae todas las marcas.");
        return modelService.listar();
    }

    @GetMapping("/marca/{id}")
    public ResponseEntity<MarcaResponse> getPorId(@PathVariable Integer id){
        Marca model = modelService.buscarPorId(id);

        if(model == null){
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
        MarcaResponse marcaResponse = MarcaMapper.toMarcaResponse(model);
        return ResponseEntity.ok(marcaResponse);
    }
    
    @PostMapping("/marca")
    public MarcaResponse guardar(@RequestBody @Valid NewMarcaRequest newMarcaRequest){
        return modelService.guardar(newMarcaRequest);
    }

    @PutMapping("/marca")
    public MarcaResponse actualizar(@RequestBody @Valid NewMarcaRequest newMarcaRequest){
        return modelService.guardar(newMarcaRequest);
    }

    @PutMapping("/marca/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable Integer id) {
        Marca model = modelService.buscarPorId(id);
        if (model == null) {
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        }


        modelService.recuperar(model); // Guarda el modelo actualizado
        return ResponseEntity.ok().build(); // Respuesta vacía con estado 200 OK
    }

    @DeleteMapping("/marca/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {


        Marca model = modelService.buscarPorId(id);
        if (model == null){
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        }

        modelService.eliminar(model);
        return ResponseEntity.ok().build();
    }
}
