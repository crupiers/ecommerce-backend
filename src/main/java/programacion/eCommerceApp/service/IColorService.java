package programacion.eCommerceApp.service;

import programacion.eCommerceApp.controller.request.NewColorRequest;
import programacion.eCommerceApp.controller.response.ColorResponse;
import programacion.eCommerceApp.model.Color;

import java.util.List;

//en esta interfaz defino lo que puede hacer y debe hacer mi service
public interface IColorService {

    List<ColorResponse> listar(); //listo de forma automática todos los colores no borrados
    Color buscarPorNombre(String nombre);

    //guardamos una entidad
    Color guardar(Color color);
    //guardamos la entidad, pero aplicando request y response
    ColorResponse guardar(NewColorRequest newColorRequest);

    //metodos que aplica la clase del modelo y que son accesibles
    void eliminar(Color model);
    void recuperar (Color model);

}
