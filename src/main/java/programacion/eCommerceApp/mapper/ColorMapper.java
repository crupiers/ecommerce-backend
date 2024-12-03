package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewColorRequest;
import programacion.eCommerceApp.controller.response.ColorResponse;
import programacion.eCommerceApp.model.Color;

public class ColorMapper {

    public ColorMapper() {
    }

    public static ColorResponse toColorResponse(final Color color) {
        return new ColorResponse(color.getId(), color.getNombre(), color.getDescripcion(), color.getEstado());
    }

    public static Color toEntity(final NewColorRequest newColorRequest) {
        return Color.builder()
                .nombre(newColorRequest.nombre())
                .descripcion(newColorRequest.descripcion())
                .build();
    }

}
