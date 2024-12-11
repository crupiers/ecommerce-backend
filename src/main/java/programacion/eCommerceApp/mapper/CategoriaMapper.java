package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewCategoriaRequest;
import programacion.eCommerceApp.controller.response.CategoriaResponse;
import programacion.eCommerceApp.model.Categoria;

public class CategoriaMapper {
    
    public static CategoriaResponse toCategoriaResponse(Categoria categoria) {
        return new CategoriaResponse(
            categoria.getId(),
            categoria.getNombre(),
            categoria.getDescripcion(),
            categoria.getEstado(),
            categoria.getCreatedBy(),
            categoria.getCreatedAt(),
            categoria.getUpdatedBy(),
            categoria.getUpdatedAt(),
            categoria.getDeletedAt()
        );
    }

    public static Categoria toEntity(NewCategoriaRequest newCategoriaRequest) {
        return Categoria.builder()
                .nombre(newCategoriaRequest.nombre())
                .descripcion(newCategoriaRequest.descripcion())
                .build();
    }

}
