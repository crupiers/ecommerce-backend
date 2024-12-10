package programacion.eCommerceApp.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import programacion.eCommerceApp.controller.request.NewMovimientoStockRequest;
import programacion.eCommerceApp.controller.response.MovimientoStockResponse;
import programacion.eCommerceApp.mapper.MovimientoStockMapper;
import programacion.eCommerceApp.model.MovimientoStock;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.repository.IMovimientoStockRepository;
import programacion.eCommerceApp.repository.IProductoRepository;

import java.util.Optional;

@Service
public class MovimientoStockService implements IMovimientoStockService {
    @Autowired
    private IMovimientoStockRepository movimientoStockRepository;
    @Autowired
    private IProductoRepository productoRepository;

    @Override
    public MovimientoStockResponse crear(Integer productoId,NewMovimientoStockRequest newMovimientoStockRequest) {
        // Buscar el producto asociado al movimiento
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el producto con ID: " + productoId));

        // Calcular el nuevo stock basado en el tipo de movimiento
        if (newMovimientoStockRequest.tipoMovimiento().equals(MovimientoStock.entrada)) {
            producto.setStock(producto.getStock() + newMovimientoStockRequest.cantidad());
        } else if (newMovimientoStockRequest.tipoMovimiento().equals(MovimientoStock.salida)) {
            if (producto.getStock() < newMovimientoStockRequest.cantidad()) {
                throw new IllegalArgumentException("El stock del producto es insuficiente para realizar la salida.");
            }
            producto.setStock(producto.getStock() - newMovimientoStockRequest.cantidad());
        } else {
            throw new IllegalArgumentException("Tipo de movimiento no válido.");
        }

        // Crear y guardar el nuevo movimiento
        MovimientoStock movimiento = new MovimientoStock();
        movimiento.setCantidad(newMovimientoStockRequest.cantidad());
        movimiento.setTipoMovimiento(newMovimientoStockRequest.tipoMovimiento());
        movimiento.setMotivo(newMovimientoStockRequest.motivo());
        movimiento.setProducto(producto);

        // Persistir el movimiento y actualizar el producto
        movimientoStockRepository.save(movimiento);
        productoRepository.save(producto);

        // Retornar la respuesta
        return MovimientoStockMapper.toMovimientoStockResponse(movimiento);
    }


}

