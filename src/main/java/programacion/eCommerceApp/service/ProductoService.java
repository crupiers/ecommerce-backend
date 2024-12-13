package programacion.eCommerceApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
    public List<ProductoResponse> listar() {
        List<Producto> productos = modelRepository.findByEstado(Producto.COMUN);
        return productos.stream().map(ProductoMapper::toProductoResponse).toList();
    }

    @Override
    public ResponseEntity<ProductoResponse> buscarPorId(Integer id) {
        Producto model = modelRepository.findById(id).orElse(null);
        if(model == null || model.getEstado() == Producto.ELIMINADO){
            throw new IllegalArgumentException("NO SE ENCONTRÓ EL PRODUCTO CON ID '" + id + "'");
        }
        ProductoResponse productoResponse = ProductoMapper.toProductoResponse(model);
        return ResponseEntity.ok(productoResponse);
    }

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

        if (newProductoRequest.stock() < 0 || newProductoRequest.stock() > 1000000) {
            throw new IllegalArgumentException("El stock debe estar entre 0 y 1.000.000");
        }

        if (newProductoRequest.precio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        if (newProductoRequest.codigoBarra() < 0) {
            throw new IllegalArgumentException("El código de barra no puede ser negativo");
        }

        if (newProductoRequest.umbral() < 0) {
            throw new IllegalArgumentException("El umbral no puede ser negativo");
        }

        if (newProductoRequest.stock() <= newProductoRequest.umbral()) {
            throw new IllegalArgumentException("El stock no puede ser menor o igual al umbral");
        }

        Producto model = ProductoMapper.toEntity(newProductoRequest, color, tamanio, categoria, marca);

        Optional<Producto> productoOptional = modelRepository.findByNombre(newProductoRequest.nombre());

        if(productoOptional.isPresent()){
            Producto productoExistente = productoOptional.get();
            if (productoExistente.getEstado() == Producto.ELIMINADO) {
                productoExistente.recuperar();
                productoExistente.setNombre(model.getNombre());
                productoExistente.setDescripcion(model.getDescripcion());
                productoExistente.setStock(model.getStock());
                productoExistente.setUmbral(model.getUmbral());
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
            if(productoOptional.get().getEstado()==Producto.ELIMINADO){
                throw new IllegalArgumentException("EL PRODUCTO CON ID '"+id+"' QUE SE QUIERE ACTUALIZAR ESTÁ ELIMINADO");
            }
            Producto productoExistente = productoOptional.get();
            productoExistente.setNombre(model.getNombre());
            productoExistente.setDescripcion(model.getDescripcion());
            productoExistente.setStock(model.getStock());
            productoExistente.setUmbral(model.getUmbral());
            productoExistente.setCodigoBarra(model.getCodigoBarra());
            productoExistente.setPrecio(model.getPrecio());
            productoExistente.setColor(model.getColor());
            productoExistente.setTamanio(model.getTamanio());
            productoExistente.setCategoria(model.getCategoria());
            productoExistente.setMarca(model.getMarca());

            return ProductoMapper.toProductoResponse(modelRepository.save(productoExistente));
        }
        throw new IllegalArgumentException("EL PRODUCTO CON ID '"+id+"' QUE SE QUIERE ACTUALIZAR NO EXISTE");
    }

    @Override
    public ResponseEntity<Void> eliminar(Integer id){
        Producto model = modelRepository.findById(id).orElse(null);
        if (model == null || model.getEstado() == Producto.ELIMINADO) {
            throw new IllegalArgumentException("NO SE ENCONTRÓ EL PRODUCTO CON ID '" + id + "'");
        }
        model.setEstado(Producto.ELIMINADO);
        modelRepository.save(model);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> recuperar(Integer id){
        Producto model = modelRepository.findById(id).orElse(null);
        if(model == null || model.getEstado() == Producto.COMUN){
            throw new IllegalArgumentException("NO SE ENCONTRÓ EL PRODUCTO CON ID '" + id + "'");
        }
        model.recuperar();
        modelRepository.save(model);
        return ResponseEntity.ok().build();
    }

}
