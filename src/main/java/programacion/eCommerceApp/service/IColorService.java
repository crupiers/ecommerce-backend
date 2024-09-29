package programacion.eCommerceApp.service;

import programacion.eCommerceApp.controller.request.NewColorRequest;
import programacion.eCommerceApp.controller.response.ColorResponse;
import programacion.eCommerceApp.model.Color;

import java.util.List;

//en esta interfaz defino lo que puede hacer y debe hacer mi service
public interface IColorService {

    List<ColorResponse> listar(); //listo de forma automática todos los colores no borrados
    Color buscarPorId(Integer id);

    //guardamos la entidad, pero aplicando request y response
    ColorResponse crear(NewColorRequest newColorRequest);

    //metodos que aplica la clase del modelo y que son accesibles
    void eliminar(Color model);
    void recuperar (Color model);

}
