package programacion.eCommerceApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import programacion.eCommerceApp.controller.request.NewProductoRequest;
import programacion.eCommerceApp.controller.response.ProductoResponse;
import programacion.eCommerceApp.mapper.ProductoMapper;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.model.Tamanio;
import programacion.eCommerceApp.model.Categoria;
import programacion.eCommerceApp.model.Marca;
import programacion.eCommerceApp.model.Color;
import programacion.eCommerceApp.repository.*;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {

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
    private static final String MENSAJE_ID_NOENCONTRADO = "NO SE ENCONTRÓ EL PRODUCTO CON ID: ";

    @Override
    public List<ProductoResponse> listar() {
        List<Producto> productos = modelRepository.findByEstado(Producto.COMUN);
        return productos.stream().map(ProductoMapper::toProductoResponse).toList();
    }

    @Override
    public ResponseEntity<ProductoResponse> buscarPorId(final Integer id) {
        Producto model = modelRepository.findById(id).orElse(null);
        if (model == null || model.getEstado() == Producto.ELIMINADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MENSAJE_ID_NOENCONTRADO + id);
        }
        ProductoResponse productoResponse = ProductoMapper.toProductoResponse(model);
        return ResponseEntity.ok(productoResponse);
    }

    @Override
    public ProductoResponse crear(final NewProductoRequest newProductoRequest) {

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

        if (productoOptional.isPresent()) {
            Producto productoExistente = productoOptional.get();
            if (productoExistente.getEstado() == Producto.ELIMINADO) {
                productoExistente.recuperar();
                productoExistente.setNombre(model.getNombre());
                productoExistente.setDescripcion(model.getDescripcion());
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
    public ProductoResponse actualizar(final NewProductoRequest newProductoRequest, final Integer id) {
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
            if (productoOptional.get().getEstado() == Producto.ELIMINADO) {
                throw new IllegalArgumentException("EL PRODUCTO CON ID '"
                        + id
                        + "' QUE SE QUIERE ACTUALIZAR ESTÁ ELIMINADO");
            }
            Producto productoExistente = productoOptional.get();
            productoExistente.setNombre(model.getNombre());
            productoExistente.setDescripcion(model.getDescripcion());
            productoExistente.setStock(model.getStock());
            productoExistente.setCodigoBarra(model.getCodigoBarra());
            productoExistente.setPrecio(model.getPrecio());
            productoExistente.setColor(model.getColor());
            productoExistente.setTamanio(model.getTamanio());
            productoExistente.setCategoria(model.getCategoria());
            productoExistente.setMarca(model.getMarca());

            return ProductoMapper.toProductoResponse(modelRepository.save(productoExistente));
        }
        throw new IllegalArgumentException("EL PRODUCTO CON ID '" + id + "' QUE SE QUIERE ACTUALIZAR NO EXISTE");
    }

    @Override
    public ResponseEntity<Void> eliminar(final Integer id) {
        Producto model = modelRepository.findById(id).orElse(null);
        if (model == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MENSAJE_ID_NOENCONTRADO + id);
        }
        model.eliminar();
        modelRepository.save(model);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> recuperar(final Integer id) {
        Producto model = modelRepository.findById(id).orElse(null);
        if(model == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MENSAJE_ID_NOENCONTRADO + id);
        }
        model.recuperar();
        modelRepository.save(model);
        return ResponseEntity.ok().build();
    }

}
