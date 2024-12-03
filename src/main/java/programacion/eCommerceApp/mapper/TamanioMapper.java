package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewTamanioRequest;
import programacion.eCommerceApp.controller.response.TamanioResponse;
import programacion.eCommerceApp.model.Tamanio;

public class TamanioMapper {

    public TamanioMapper() {
    }

    public static TamanioResponse toTamanioResponse(final Tamanio tamanio) {
        return new TamanioResponse(tamanio.getId(), tamanio.getNombre(), tamanio.getDescripcion(), tamanio.getEstado());
    }

    public static Tamanio toEntity(final NewTamanioRequest newTamanioRequest) {
        return Tamanio.builder()
                .nombre(newTamanioRequest.nombre())
                .descripcion(newTamanioRequest.descripcion())
                .build();
    }

}
