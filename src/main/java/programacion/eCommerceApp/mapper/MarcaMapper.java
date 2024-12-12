package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewMarcaRequest;
import programacion.eCommerceApp.controller.response.MarcaResponse;
import programacion.eCommerceApp.model.Marca;

public class MarcaMapper {
    public static MarcaResponse toMarcaResponse(Marca marca) {
        return new MarcaResponse(
            marca.getId(),
            marca.getNombre(),
            marca.getDescripcion(),
            marca.getEstado(),
            marca.getCreatedBy(),
            marca.getCreatedAt(),
            marca.getUpdatedBy(),
            marca.getUpdatedAt(),
            marca.getDeletedAt()
        );
    }

    public static Marca toEntity(NewMarcaRequest newMarcaRequest) {
        return Marca.builder().nombre(newMarcaRequest.nombre()).descripcion(newMarcaRequest.descripcion()).build();
    }

}
