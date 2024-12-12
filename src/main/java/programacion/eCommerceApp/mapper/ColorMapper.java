package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewColorRequest;
import programacion.eCommerceApp.controller.response.ColorResponse;
import programacion.eCommerceApp.model.Color;

public class ColorMapper {

    public static ColorResponse toColorResponse(Color color) {
        return new ColorResponse(
            color.getId(),
            color.getNombre(),
            color.getDescripcion(),
            color.getEstado(),
            color.getCreatedBy(),
            color.getCreatedAt(),
            color.getUpdatedBy(),
            color.getUpdatedAt(),
            color.getDeletedAt());
    }

    public static Color toEntity(NewColorRequest newColorRequest) {
        return Color.builder()
            .nombre(newColorRequest.nombre())
            .descripcion(newColorRequest.descripcion())
            .build();
    }

}
