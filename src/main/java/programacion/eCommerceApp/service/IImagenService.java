package programacion.eCommerceApp.service;

import programacion.eCommerceApp.controller.request.NewImagenRequest;
import programacion.eCommerceApp.controller.response.ImagenResponse;

import java.util.List;

public interface IImagenService {

    ImagenResponse crear(NewImagenRequest newImagenRequest);
    List<ImagenResponse> listar();

}
