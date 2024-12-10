package programacion.eCommerceApp.controller.response;

public record MovimientoStockResponse(
        Integer id,
        Integer cantidad,
        String motivo,
        String tipoMovimiento
) {
}
