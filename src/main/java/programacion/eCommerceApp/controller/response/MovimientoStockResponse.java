package programacion.eCommerceApp.controller.response;

public record MovimientoStockResponse(
        Integer id,
        Integer cantidad,
        String motivo,
        String tipoMovimiento,
        String createdBy,
        String fechaMovimiento,
        String horaMovimiento
) {
}
