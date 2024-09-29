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

@RestController //aclaro que la clase es un controlador
@RequestMapping("eCommerce") //mapeo para que sea accesible
@CrossOrigin(value=" http://localhost:8080") //hago accesible el endpoint por el puerto 8080

//esta clase no va a necesitar un metodo de actualizacion ya que solo se guarda el nombre de un color
public class ColorController {

    //genero el logger, que me permite informar al usuario
    private static final Logger logger = LoggerFactory.getLogger(ColorController.class);

    @Autowired //autowired me sirve que las dependencias se injecten solas
    private IColorService modelService; //el service se encarga de realizar las tareas
    //el controller es quien me permite acceder al service (a la funcionalidad)

    //indico a la instrucción "GET" del postman que ejecute ese metodo mediante ese endpoint
    @GetMapping({"/color"})
    public List<ColorResponse> getAll(){
        //devuelvo una lista de "ColorResponse" y no "Color" ya que la entidad no se usa directamente
        //con "ColorResponse" se muestra lo que queremos y no todos los atributos al usuario
        //ahora informo la acción ejecutada
        logger.info("ESTOS SON TODOS LOS COLORES DISPONIBLES");
        //llamo al service que tiene la lógica de listado de colores
        return modelService.listar();
    }

    @GetMapping("/color/{id}") //busco el color por su nombre
    //la clase "ResponseEntity" permite devolver un mensaje HTTP, de confirmación por ejemplo
    //para ello debe de entender o recibir las respuestas de colores "ColorResponse"
    //tambien, como nuestra primary key es el nombre de color, este será el parametro ingresado
    //con "@PathVariable" estamos tomando el "{nombre}" de la URL, tomamos el valor que se ingresa por él
    public ResponseEntity<ColorResponse> buscarPorId(@PathVariable Integer id){
        //usamos el servicio para encontrar un color según su nombre
        Color model = modelService.buscarPorId(id);
        if(model==null || model.getEstado() == Color.ELIMINADO){
            //si no se encuentra el color se manda una excepcion
            //usamos nuestra excepcion personalizada
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL COLOR: "+id);
        }
        //creo una respuesta en base al color tomado de la bbdd
        ColorResponse colorResponse = ColorMapper.toColorResponse(model);
        //haciendo uso de la clase "ResponseEntity" devolvemos un estado HTTP de que el color fue encontrado
        //se manda "ColorResponse" ya que este será el "body" o cuerpo que se muestra en la devolución de informacion
        return ResponseEntity.ok(colorResponse);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/color") //uso de la instruccion "POST"
    /**
     * "@RequestBody" es una anotacion que sirve para tomar el texto o los datos que se muestran
     * en pantalla de la clase "NewColorRequest"
     * Es decir que lo que ingresamos como JSON es validado con "@Valid" para utilizarse por la request
     */
    public ColorResponse crear(@RequestBody @Valid NewColorRequest newColorRequest){
        return modelService.crear(newColorRequest);
    }

    public ColorResponse actualizar(@RequestBody @Valid NewColorRequest newColorRequest, @PathVariable Integer id){
        return null;
    }

    @PutMapping("/color/recuperar/{id}") //mapeamos el "PUT" para volver a recuperar un color eliminado
    //se utiliza un elemento "Void" como respuesta ya que no se envía ninguna otra info
    //más que realizar la accion de recuperado
    //El "PUT" lo usamos para actualizar, como no tiene sentido actualizar un registro
    //que sólo tiene nombre y estado, el put es usado para recuperar un color el cual sabemos su nombre
    //(que es el id)
    public ResponseEntity<Void> recuperar(@PathVariable Integer id){
        Color model = modelService.buscarPorId(id);
        if(model==null){
            //en caso de que se quiera recuperar un color borrado que en realidad nunca fue registrado
            //lanzamos excepcion
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EL COLOR '"+id+ "' NUNCA FUE REGISTRADO NI BORRADO ANTERIORMENTE");
        }
        //si encuentro el color que quiero recuperar
        modelService.recuperar(model); //llamo al servicio para que se encargue de recuperarlo
        //notese que no se utiliza el metodo "recuperar()" de la variable "model"
        //ya que de esto se va a encargar el metodo "recuperar()" del "modelService"
        return ResponseEntity.ok().build(); //construyo una entidad de respuesta vacía (por eso "Void" es el generico "T")
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

}
