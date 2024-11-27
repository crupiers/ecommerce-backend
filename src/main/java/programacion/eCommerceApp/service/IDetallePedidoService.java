package programacion.eCommerceApp.service;

import org.springframework.http.ResponseEntity;
import programacion.eCommerceApp.controller.request.NewDetallePedidoRequest;
import programacion.eCommerceApp.controller.response.DetallePedidoResponse;
import programacion.eCommerceApp.model.Producto;

import java.util.List;

public interface IDetallePedidoService {

    DetallePedidoResponse crear(NewDetallePedidoRequest newDetallePedidoRequest);
    List<DetallePedidoResponse> listar();
    DetallePedidoResponse buscarPorId(Integer id);
}
