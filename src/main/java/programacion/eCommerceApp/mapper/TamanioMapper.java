package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewTamanioRequest;
import programacion.eCommerceApp.controller.response.TamanioResponse;
import programacion.eCommerceApp.model.Tamanio;

public class TamanioMapper {

    public static TamanioResponse toTamanioResponse(Tamanio tamanio) {
        return new TamanioResponse(tamanio.getId(), tamanio.getDenominacion(), tamanio.getObservaciones(), tamanio.getEstado());
    }

    public static Tamanio toEntity (NewTamanioRequest newTamanioRequest) {
        return Tamanio.builder()
                .denominacion(newTamanioRequest.denominacion())
                .observaciones(newTamanioRequest.observaciones())
                .build();
    }

}
