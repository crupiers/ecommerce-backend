package programacion.eCommerceApp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.Mapper.CategoriaMapper;
import programacion.ejemplo.exception.RecursoNoEncontradoExcepcion;
import programacion.ejemplo.service.ICategoriaService;
import programacion.ejemplo.model.Categoria;

import java.util.List;

@RestController
@RequestMapping("eCommerce")
@CrossOrigin(value=" http://localhost:5173")

public class CategoriaController {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);
    @Autowired
    private ICategoriaService modelService;

    @GetMapping({"/Categorias"})
    public List<CategoriaDTO> getAll() {
        logger.info("entra y trae todas las Categorias");
        return modelService.listar();

    }

    @GetMapping("/Categoria/{id}")
    public ResponseEntity<CategoriaDTO> getPorId(@PathVariable Integer id){
        Categoria model = modelService.buscarPorId(id);

        if(model == null){
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
        CategoriaDTO modelDTO = CategoriaMapper.toDTO(model);
        return ResponseEntity.ok(modelDTO);
    }


    
    @PostMapping("/Categoria")
    public CategoriaDTO guardar(@RequestBody CategoriaDTO model){
        return modelService.guardar(model);
    }

    @PutMapping("/Categoria")
    public CategoriaDTO actualizar(@RequestBody CategoriaDTO model){

        return modelService.guardar(model);
    }

    @DeleteMapping("/Categoria/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {


        Categoria model = modelService.buscarPorId(id);
        if (model == null){
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        }

        model.eliminar();
        modelService.eliminar(model);
        return ResponseEntity.ok().build();
    }
}
