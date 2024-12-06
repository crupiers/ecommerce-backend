package programacion.eCommerceApp.mapper;

import programacion.eCommerceApp.controller.request.NewPedidoRequest;
import programacion.eCommerceApp.controller.response.PedidoResponse;
import programacion.eCommerceApp.model.DetallePedido;
import programacion.eCommerceApp.model.Pedido;
import programacion.eCommerceApp.model.Usuario;

import java.util.List;

public class PedidoMapper {

    public static Pedido toEntity(NewPedidoRequest newPedidoRequest, List<DetallePedido> detallesPedido, Usuario usuario) {
        return Pedido.builder()
            .usuario(usuario)
            .detallesPedido(detallesPedido)
            .build();
    }

    public static PedidoResponse toPedidoResponse(Pedido pedido) {
        return new PedidoResponse(
            pedido.getId(),
            pedido.getHoraPedido(),
            pedido.getFechaPedido(),
            pedido.getUsuario().getNombre(),
            pedido.getDetallesPedido().stream().map(DetallePedido::getId).toList(),
            pedido.getTotal(),
            pedido.getEstado()
        );
    }

}
