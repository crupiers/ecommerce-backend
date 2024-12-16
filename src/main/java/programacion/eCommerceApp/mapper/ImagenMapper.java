package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewImagenRequest;
import programacion.eCommerceApp.controller.response.ImagenResponse;
import programacion.eCommerceApp.model.Imagen;

public class ImagenMapper {

    public static ImagenResponse toImagenResponse(Imagen imagen) {
        return new ImagenResponse(
          imagen.getId(),
          imagen.getIdProducto(),
          imagen.getImagenBase64()
        );

    }

    public static Imagen toEntity(NewImagenRequest newImagenRequest) {
        return Imagen.builder()
            .idProducto(newImagenRequest.idProducto())
            .imagenBase64(newImagenRequest.imagenBase64())
            .build();
    }

}
