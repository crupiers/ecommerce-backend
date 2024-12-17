package programacion.eCommerceApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion.eCommerceApp.controller.request.NewMovimientoStockRequest;
import programacion.eCommerceApp.controller.response.MovimientoStockResponse;
import programacion.eCommerceApp.mapper.MovimientoStockMapper;
import programacion.eCommerceApp.model.MovimientoStock;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.repository.IMovimientoStockRepository;
import programacion.eCommerceApp.repository.IProductoRepository;
import java.util.List;

@Service
public class MovimientoStockService implements IMovimientoStockService {
    @Autowired
    private IMovimientoStockRepository movimientoStockRepository;
    @Autowired
    private IProductoRepository productoRepository;
    @Autowired
    private EmailService emailService;

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

            boolean estabaDebajoDelUmbral = producto.getStock() < producto.getUmbral();

            producto.setStock(producto.getStock() - newMovimientoStockRequest.cantidad());

            if (producto.getStock() < producto.getUmbral() && !estabaDebajoDelUmbral){
                emailService.sendEmail(
                    "moranofrancisco1234@gmail.com",
                    "Alerta de stock bajo para producto '" + producto.getNombre() + "'",
                    "El stock del producto '" + producto.getNombre() + "' ha alcanzado las '" + producto.getStock() + "' unidades, debajo de su umbral definido de '" + producto.getUmbral() + "'."
                );
            }

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

    public List<MovimientoStockResponse> listarParaAuditoria() {
        List<MovimientoStock> movimientos = movimientoStockRepository.findAll();
        return movimientos.stream().map(MovimientoStockMapper::toMovimientoStockResponse).toList();
    }
}

