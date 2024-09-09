package programacion.eCommerceApp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import programacion.eCommerceApp.DTO.MarcaDTO;
import programacion.eCommerceApp.Mapper.MarcaMapper;
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
    public List<MarcaDTO> getAll() {
        logger.info("entra y trae todas las marcas");
        return modelService.listar();

    }

    @GetMapping("/marca/{id}")
    public ResponseEntity<MarcaDTO> getPorId(@PathVariable Integer id){
        Marca model = modelService.buscarPorId(id);

        if(model == null){
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
        MarcaDTO modelDTO = MarcaMapper.toDTO(model);
        return ResponseEntity.ok(modelDTO);
    }


    
    @PostMapping("/marca")
    public MarcaDTO guardar(@RequestBody MarcaDTO model){
        return modelService.guardar(model);
    }

    @PutMapping("/marca")
    public MarcaDTO actualizar(@RequestBody MarcaDTO model){

        return modelService.guardar(model);
    }
    @PutMapping("/marca/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable Integer id) {
        Marca model = modelService.buscarPorId(id);
        if (model == null) {
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        }

        model.recuperar(); // Cambia el estado a COMUN
        modelService.guardar(model); // Guarda el modelo actualizado

        return ResponseEntity.ok().build(); // Respuesta vacía con estado 200 OK
    }




    @DeleteMapping("/marca/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {


        Marca model = modelService.buscarPorId(id);
        if (model == null){
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        }

        model.asEliminar();
        modelService.eliminar(model);
        return ResponseEntity.ok().build();
    }
}
