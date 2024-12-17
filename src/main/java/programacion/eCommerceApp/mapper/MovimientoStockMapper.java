package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewMovimientoStockRequest;
import programacion.eCommerceApp.controller.response.MovimientoStockResponse;
import programacion.eCommerceApp.model.MovimientoStock;

import java.util.List;

public class MovimientoStockMapper {
    public static MovimientoStockResponse toMovimientoStockResponse(MovimientoStock movimientoStock){
        return new MovimientoStockResponse(
                movimientoStock.getId(),
                movimientoStock.getCantidad(),
                movimientoStock.getMotivo(),
                movimientoStock.getTipoMovimiento(),
                movimientoStock.getCreatedBy(),
                movimientoStock.getFechaPedido(),
                movimientoStock.getHoraPedido()
        );
    }
    public static MovimientoStock toEntity(NewMovimientoStockRequest newMovimientoStockRequest){
        return MovimientoStock.builder()
                .cantidad(newMovimientoStockRequest.cantidad())
                .motivo(newMovimientoStockRequest.motivo())
                .tipoMovimiento(newMovimientoStockRequest.tipoMovimiento())
                .build();
    }

}
