package programacion.eCommerceApp.service;

import programacion.eCommerceApp.controller.request.NewColorRequest;
import programacion.eCommerceApp.controller.response.ColorResponse;
import programacion.eCommerceApp.model.Color;

import java.util.List;
public interface IColorService {

    List<ColorResponse> listar(); //listo de forma automática todos los colores no borrados
    Color buscarPorId(Integer id);
    Color buscarPorNombre (String nombre);

    //guardamos la entidad, pero aplicando request y response
    ColorResponse crear(NewColorRequest newColorRequest);
    ColorResponse actualizar(NewColorRequest newColorRequest, Integer id);

    void eliminar(Color model);
    void recuperar (Color model);
}
