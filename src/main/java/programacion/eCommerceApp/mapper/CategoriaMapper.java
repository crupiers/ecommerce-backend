package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewCategoriaRequest;
import programacion.eCommerceApp.controller.response.CategoriaResponse;
import programacion.eCommerceApp.model.Categoria;

public class CategoriaMapper {

    public CategoriaMapper() {
    }

    public static CategoriaResponse toCategoriaResponse(final Categoria model) {
        return new CategoriaResponse(model.getId(), model.getNombre(), model.getDescripcion(), model.getEstado());
    }

    public static Categoria toEntity(final NewCategoriaRequest newCategoriaRequest) {
        return Categoria.builder()
                .nombre(newCategoriaRequest.nombre())
                .descripcion(newCategoriaRequest.descripcion())
                .build();
    }

}
