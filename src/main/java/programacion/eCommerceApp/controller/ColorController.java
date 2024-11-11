package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import programacion.eCommerceApp.controller.request.NewColorRequest;
import programacion.eCommerceApp.controller.response.ColorResponse;
import programacion.eCommerceApp.mapper.ColorMapper;
import programacion.eCommerceApp.model.Color;
import programacion.eCommerceApp.service.IColorService;

import java.util.List;

@RestController 
@RequestMapping("eCommerce") //mapeo para que sea accesible
@CrossOrigin(value=" http://localhost:8080") //hago accesible el endpoint por el puerto 8080

//esta clase no va a necesitar un metodo de actualizacion ya que solo se guarda el nombre de un color
public class ColorController {

    //genero el logger, que me permite informar al usuario
    private static final Logger logger = LoggerFactory.getLogger(ColorController.class);

    @Autowired //autowired me sirve que las dependencias se injecten solas
    private IColorService modelService; //el service se encarga de realizar las tareas

    @GetMapping({"/color"})
    public List<ColorResponse> getAll(){
        logger.info("ESTOS SON TODOS LOS COLORES DISPONIBLES");

        return modelService.listar();
    }

    @GetMapping("/color/{id}") //busco el color por su nombre
    public ResponseEntity<ColorResponse> buscarPorId(@PathVariable Integer id){
        //usamos el servicio para encontrar un color según su nombre
        Color model = modelService.buscarPorId(id);
        if(model==null || model.getEstado() == Color.ELIMINADO){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL COLOR: "+id);
        }
        ColorResponse colorResponse = ColorMapper.toColorResponse(model);

        return ResponseEntity.ok(colorResponse);
    }

    @ResponseStatus(HttpStatus.CREATED)
    
    @PostMapping("/color") //uso de la instruccion "POST"
    public ColorResponse crear(@RequestBody @Valid NewColorRequest newColorRequest){
        return modelService.crear(newColorRequest);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/color/actualizar/{id}")
    public ColorResponse actualizar(@RequestBody @Valid NewColorRequest newColorRequest, @PathVariable Integer id){
        return modelService.actualizar(newColorRequest, id);
    }

    @PutMapping("/color/recuperar/{id}") //mapeamos el "PUT" para volver a recuperar un color eliminado

    public ResponseEntity<Void> recuperar(@PathVariable Integer id){
        Color model = modelService.buscarPorId(id);
        if(model==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EL COLOR '"+id+ "' NUNCA FUE REGISTRADO NI BORRADO ANTERIORMENTE");
        }
        //si encuentro el color que quiero recuperar
        modelService.recuperar(model); //llamo al servicio para que se encargue de recuperarlo

        return ResponseEntity.ok().build(); 
    }

    @DeleteMapping("/color/{id}") //mapeo el "DELETE" del postman para que realice esta funcion de eliminado logico
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        Color model = modelService.buscarPorId(id);
        if(model==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EL COLOR '"+id+"' NO EXISTE Y NO PUEDE SER BORRADO");
        }
        modelService.eliminar(model);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color/existe/{nombre}")
    public Color buscarPorNombre(@PathVariable String nombre){
        Color model = modelService.buscarPorNombre(nombre);
        return model;
    }

}
