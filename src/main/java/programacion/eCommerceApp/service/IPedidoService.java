package programacion.eCommerceApp.service;

import programacion.eCommerceApp.controller.request.NewPedidoRequest;
import programacion.eCommerceApp.controller.response.PedidoResponse;
import java.util.List;

public interface IPedidoService {

    PedidoResponse crear(NewPedidoRequest newPedidoRequest);
    List<PedidoResponse> listar();
    PedidoResponse buscarPorId(Integer id);
    List<PedidoResponse> buscarPorUsuario(Integer idUsuario);
    PedidoResponse eliminar(Integer id);

}
