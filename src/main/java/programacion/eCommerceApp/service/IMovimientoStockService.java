package programacion.eCommerceApp.service;

import programacion.eCommerceApp.controller.request.NewMovimientoStockRequest;
import programacion.eCommerceApp.controller.response.MovimientoStockResponse;
import programacion.eCommerceApp.controller.response.ProductoResponse;

import java.util.List;

public interface IMovimientoStockService {
    MovimientoStockResponse crear(Integer productoId, NewMovimientoStockRequest newMovimientoStockRequest);
    List<MovimientoStockResponse> listarParaAuditoria();
}
