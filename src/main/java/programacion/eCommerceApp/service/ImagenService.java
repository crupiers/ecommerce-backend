package programacion.eCommerceApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion.eCommerceApp.controller.request.NewImagenRequest;
import programacion.eCommerceApp.controller.response.ImagenResponse;
import programacion.eCommerceApp.mapper.ImagenMapper;
import programacion.eCommerceApp.model.Imagen;
import programacion.eCommerceApp.repository.IImagenRepository;

import java.util.List;

@Service
public class ImagenService implements IImagenService{

    @Autowired
    private IImagenRepository imagenRepository;

    public ImagenResponse crear(NewImagenRequest newImagenRequest) {
        Imagen imagen = ImagenMapper.toEntity(newImagenRequest);
        return ImagenMapper.toImagenResponse(imagenRepository.save(imagen));
    }

    public List<ImagenResponse> listar() {
        List<Imagen> imagenes = imagenRepository.findAll();
        return imagenes.stream().map(ImagenMapper::toImagenResponse).toList();
    }

}
