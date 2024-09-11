package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewCategoriaRequest;
import programacion.eCommerceApp.controller.response.CategoriaResponse;
import programacion.eCommerceApp.model.Categoria;

public class CategoriaMapper {
    
    public static CategoriaResponse toCategoriaResponse(Categoria model) {
        return new CategoriaResponse(model.getId(), model.getNombre(), model.getEstado());
    }

    public static Categoria toEntity(NewCategoriaRequest newCategoriaRequest) {
        return Categoria.builder()
                .nombre(newCategoriaRequest.nombre())
                .build();
    }

}
