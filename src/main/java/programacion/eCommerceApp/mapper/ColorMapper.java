package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewColorRequest;
import programacion.eCommerceApp.controller.response.ColorResponse;
import programacion.eCommerceApp.model.Color;

public class ColorMapper {

    public static ColorResponse toColorResponse(Color color){
        //creo la respuesta con los datos necesarios
        return new ColorResponse(color.getId(), color.getNombre(), color.getEstado());
    }

    public static Color toEntity(NewColorRequest newColorRequest){
        //creo la entidad con los datos que me pasó el usuario
        //no estoy pasando el estado (visible o no)
        //ya que este no se ingresa por el usuario
        return Color.builder()
                .nombre(newColorRequest.nombre())
                .build();
        //al momento de construir, "@Builder.Default" se encarga de los atributos que no son pasados
        //en este caso, "visible" queda en "true" aunque nunca se pasa
    }

}
