package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewTamañoRequest;
import programacion.eCommerceApp.controller.response.TamañoResponse;
import programacion.eCommerceApp.model.Tamaño;

public class TamañoMapper {

    public static TamañoResponse toTamañoResponse(Tamaño tamaño) {
        return new TamañoResponse(tamaño.getId(),tamaño.getDenominacion(), tamaño.getEstado());
    }

    public static Tamaño toEntity (NewTamañoRequest newTamañoRequest) {
        return Tamaño.builder()
                .denominacion(newTamañoRequest.denominacion())
                .build();
    }

}
