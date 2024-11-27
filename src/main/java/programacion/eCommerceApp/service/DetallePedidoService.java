package programacion.eCommerceApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import programacion.eCommerceApp.controller.request.NewDetallePedidoRequest;
import programacion.eCommerceApp.controller.response.DetallePedidoResponse;
import programacion.eCommerceApp.mapper.DetallePedidoMapper;
import programacion.eCommerceApp.model.Categoria;
import programacion.eCommerceApp.model.DetallePedido;
import programacion.eCommerceApp.model.Producto;
import programacion.eCommerceApp.repository.IDetallePedidoRepository;
import programacion.eCommerceApp.repository.IProductoRepository;
import java.util.List;

@Service
public class DetallePedidoService implements IDetallePedidoService {

    @Autowired
    private IProductoRepository productoRepository;
    @Autowired
    private IDetallePedidoRepository detallePedidoRepository;

    public DetallePedidoResponse crear(NewDetallePedidoRequest newDetallePedidoRequest) {

        Producto producto = productoRepository.findById(newDetallePedidoRequest.idProducto())
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + newDetallePedidoRequest.idProducto()));

        DetallePedido detallePedido = DetallePedidoMapper.toEntity(newDetallePedidoRequest, producto);
        detallePedido.setSubtotal(detallePedido.calcularSubtotal());
        return DetallePedidoMapper.toDetallePedidoResponse(detallePedidoRepository.save(detallePedido));

    }

    public List<DetallePedidoResponse> listar() {
        List<DetallePedido> listaDetalles = detallePedidoRepository.findByEstado(DetallePedido.COMUN);
        return listaDetalles.stream().map(DetallePedidoMapper::toDetallePedidoResponse).toList();
    }

    public DetallePedidoResponse buscarPorId(Integer id) {
        DetallePedido model = detallePedidoRepository.findById(id).orElse(null);
        if(model == null || model.getEstado() == Categoria.ELIMINADO){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ LA CATEGORÍA CON ID: "+id);
        }
        return DetallePedidoMapper.toDetallePedidoResponse(model);
    }

}
