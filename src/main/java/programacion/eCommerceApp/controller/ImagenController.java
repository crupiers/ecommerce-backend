package programacion.eCommerceApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.eCommerceApp.controller.request.NewImagenRequest;
import programacion.eCommerceApp.controller.response.ImagenResponse;
import programacion.eCommerceApp.service.IImagenService;

import java.util.List;

@RestController
@RequestMapping("/ecommerce")
@CrossOrigin(value=" http://localhost:8080")
public class ImagenController {

    @Autowired
    private IImagenService imagenService;

    @PostMapping("/admin/imagenes")
    public ImagenResponse crear(@RequestBody NewImagenRequest newImagenRequest){
        return imagenService.crear(newImagenRequest);
    }

    @GetMapping("/imagenes")
    public List<ImagenResponse> listar(){
        return imagenService.listar();
    }

}
