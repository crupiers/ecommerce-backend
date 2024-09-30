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

        Producto model = ProductoMapper.toEntity(newProductoRequest, color, tamanio, categoria, marca);

        Optional<Producto> productoOptional = modelRepository.findByNombre(newProductoRequest.nombre());

        if(productoOptional.isPresent()){
            Producto productoExistente = productoOptional.get();
            if (productoExistente.getEstado() == Producto.ELIMINADO) {
                productoExistente.recuperar();
                productoExistente.setNombre(model.getNombre());
                productoExistente.setStock(model.getStock());
                productoExistente.setCodigoBarra(model.getCodigoBarra());
                productoExistente.setPrecio(model.getPrecio());
                productoExistente.setColor(model.getColor());
                productoExistente.setTamanio(model.getTamanio());
                productoExistente.setCategoria(model.getCategoria());
                productoExistente.setMarca(model.getMarca());

                return ProductoMapper.toProductoResponse(modelRepository.save(productoExistente));
            } else {
                throw new IllegalArgumentException("El producto ya existe");
            }
        }
        return ProductoMapper.toProductoResponse(modelRepository.save(model));
    }

    @Override
    public ProductoResponse actualizar(NewProductoRequest newProductoRequest , Integer id){
        Tamanio tamanio = tamanioRepository.findById(newProductoRequest.tamanioId())
                .orElseThrow(() -> new IllegalArgumentException("Tamaño no encontrado con ID: " + newProductoRequest.tamanioId()));

        Color color = colorRepository.findById(newProductoRequest.colorId())
                .orElseThrow(() -> new IllegalArgumentException("Color no encontrado con ID: " + newProductoRequest.colorId()));

        Categoria categoria = categoriaRepository.findById(newProductoRequest.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + newProductoRequest.categoriaId()));

        Marca marca = marcaRepository.findById(newProductoRequest.marcaId())
                .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada con ID: " + newProductoRequest.marcaId()));

        Producto model = ProductoMapper.toEntity(newProductoRequest, color, tamanio, categoria, marca);

        Optional<Producto> productoOptional = modelRepository.findByNombre(model.getNombre());

        if (productoOptional.isPresent()) {
            Producto productoExistente = productoOptional.get();
            productoExistente.setNombre(model.getNombre());
            productoExistente.setStock(model.getStock());
            productoExistente.setCodigoBarra(model.getCodigoBarra());
            productoExistente.setPrecio(model.getPrecio());
            productoExistente.setColor(model.getColor());
            productoExistente.setTamanio(model.getTamanio());
            productoExistente.setCategoria(model.getCategoria());
            productoExistente.setMarca(model.getMarca());

            return ProductoMapper.toProductoResponse(modelRepository.save(productoExistente));
        }
        else {
            throw new IllegalArgumentException("Producto no existe");
        }
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
