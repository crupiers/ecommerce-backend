package programacion.eCommerceApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.mapper.ProductoMapper;
import programacion.eCommerceApp.model.*;
import programacion.eCommerceApp.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    private IProductoRepository modelRepository;

    @Autowired
    private IColorRepository colorRepository;

    @Autowired
    private ITamanioRepository tamanioRepository;

    @Autowired
    private IMarcaRepository marcaRepository;

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Override
    public ProductoResponse crear(NewProductoRequest newProductoRequest){

        Tamanio tamanio = tamanioRepository.findById(newProductoRequest.tamanioId())
                .orElseThrow(() -> new IllegalArgumentException("Tamaño no encontrado con ID: " + newProductoRequest.tamanioId()));

        Color color = colorRepository.findById(newProductoRequest.colorId())
                .orElseThrow(() -> new IllegalArgumentException("Color no encontrado con ID: " + newProductoRequest.colorId()));

        Categoria categoria = categoriaRepository.findById(newProductoRequest.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + newProductoRequest.categoriaId()));

        Marca marca = marcaRepository.findById(newProductoRequest.marcaId())
                .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada con ID: " + newProductoRequest.marcaId()));

        Optional<Producto> productoExistente = modelRepository.findByNombre(newProductoRequest.nombre());

        if(productoExistente.isPresent()){
            throw new IllegalArgumentException("Producto ya registrado");
        }
        Producto model = ProductoMapper.toEntity(newProductoRequest, color, tamanio, categoria, marca);

        return ProductoMapper.toProductoResponse(modelRepository.save(model));


    }

    @Override
    public List<ProductoResponse> listar() {
        List<Producto> productos = modelRepository.findByEstado(Producto.COMUN);
        return productos.stream().map(ProductoMapper::toProductoResponse).toList();
    }

    @Override
    public Producto buscarPorId(Integer id) {
        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public void recuperar(Producto model){
        model.recuperar();
        modelRepository.save(model);
    }

    @Override
    public void eliminar(Producto model){
        model.eliminar();
        modelRepository.save(model);
    }
}
