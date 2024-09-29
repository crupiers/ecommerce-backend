package programacion.eCommerceApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion.eCommerceApp.controller.request.NewColorRequest;
import programacion.eCommerceApp.controller.response.ColorResponse;
import programacion.eCommerceApp.mapper.ColorMapper;
import programacion.eCommerceApp.model.Color;
import programacion.eCommerceApp.repository.IColorRepository;

import java.util.List;
import java.util.Optional;

@Service //declaro que la clase es un service

public class ColorService implements IColorService {
    //al implementar la interfaz, se tiene que declarar y programar todos lo metodos de la interfaz

    //el service usa el repository, mientras que el controller usa el service (esta clase)
    @Autowired
    private IColorRepository modelRepository;

    //la anotación "Override" quiere decir que sobreescribimos o implementamos un metodo
    //en este caso se implementa el metodo de la interfaz
    //sirve para que el compilador de java no malentienda o le sea mas facil compilar ahre
    @Override
    public List<ColorResponse> listar() {
        //busco los colores que deben ser visibles
        //debo usar el repository pues este me traduce lo que necesito a secuencias sql
        //guardo los objetos colores en una lista
        List<Color> colores = modelRepository.findByEstado(Color.COMUN);
        /**
         * stream() me devuelve un objeto "Stream" de la clase "Color"
         * un stream puede verse como un flujo de datos
         * ese flujo de datos (bits) son mapeados a la respuesta "ColorResponse" que el usuario recibe
         * al recibir objetos "Color" seguidos usamos "::" para llamar a ese metodo en cada uno de los objetos recibidos
         * así convertimos cada objeto "Color" en un "ColorResponse"
         * */
        return colores.stream().map(ColorMapper::toColorResponse).toList();
    }

    @Override
    public Color buscarPorId(Integer id) {
        //como el nombre es mi id, tengo que pasarlo como tal
        //si no encuentro, paso un objeto nulo
        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public ColorResponse crear(NewColorRequest newColorRequest) {
        Color model = ColorMapper.toEntity(newColorRequest); //de peticion a color
        //"Optional" es una clase que me indica si un objeto fue encontrado o no
        //indica si es nulo o no es nulo, es decir, está presente jejeXD
        //usamos "model" ya que debemos convertir la peticion en objeto para poder buscarla
        //recordemos que nuestro id es el nombre del color y no un número
        Optional<Color> colorExistente = modelRepository.findByNombre(model.getNombre());
        if(colorExistente.isPresent()){
            //si el color existe, es decir que encontramos por id el color según el nombre que se pide
            //lanzamos una excepción
            throw new IllegalArgumentException("EL COLOR YA ESTÁ REGISTRADO");
        }
        //si no se lanza la excepción la función sigue y devuelve una respuesta
        //"modelRepository.save(model)" lo que hace es guardar el color que se ingresó en la bbdd
        //además devuelve ese objeto creado y guardado "Color" para convertirlo en una respuesta
        return ColorMapper.toColorResponse(modelRepository.save(model));
    }

    @Override
    public ColorResponse actualizar(NewColorRequest newColorRequest, Integer id){
        Color model = ColorMapper.toEntity(newColorRequest);
        Optional<Color> colorOptional = modelRepository.findById(id);
        if(colorOptional.isPresent()){
            Color color = colorOptional.get();
            color.setNombre(model.getNombre());
            return ColorMapper.toColorResponse(modelRepository.save(color));
        }else {
            throw new IllegalArgumentException("EL COLOR CON ID '"+id+"' NO EXISTE");
        }
    }

    @Override
    public void eliminar(Color model) {
        //llamamos al metodo de la clase "Color" para que se "elimine"
        //esto cambia el estado del objeto
        model.eliminar();
        //una vez cambiado el estado, se tiene que actualizar el registro de esa entidad en la bbdd
        //con "save" realizamos tal tarea, el repository traduce la orden a sql
        modelRepository.save(model);
    }

    @Override
    public void recuperar(Color model) {
        //hago lo mismo que en "eliminar()"
        model.recuperar();
        modelRepository.save(model);
    }
}
