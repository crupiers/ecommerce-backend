package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewMarcaRequest;
import programacion.eCommerceApp.controller.response.MarcaResponse;
import programacion.eCommerceApp.model.Marca;

public class MarcaMapper {
    public static MarcaResponse toMarcaResponse(Marca marca) {
        return new MarcaResponse(marca.getId(), marca.getDenominacion(), marca.getObservaciones(), marca.getEstado());
    }

    public static Marca toEntity(NewMarcaRequest newMarcaRequest) {
        return Marca.builder().denominacion(newMarcaRequest.denominacion()).observaciones(newMarcaRequest.observaciones()).build();
    }

}
